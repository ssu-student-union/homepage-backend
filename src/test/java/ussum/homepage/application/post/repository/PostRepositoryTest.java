package ussum.homepage.application.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import ussum.homepage.domain.post.Post;
import ussum.homepage.infra.config.QuerydslConfig;
import ussum.homepage.infra.jpa.comment.repository.PostCommentJpaRepository;
import ussum.homepage.infra.jpa.comment.repository.PostReplyCommentJpaRepository;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.group.entity.GroupEntity;
import ussum.homepage.infra.jpa.member.entity.MemberCode;
import ussum.homepage.infra.jpa.member.entity.MemberEntity;
import ussum.homepage.infra.jpa.post.PostMapper;
import ussum.homepage.infra.jpa.post.PostRepositoryImpl;
import ussum.homepage.infra.jpa.post.entity.*;
import ussum.homepage.infra.jpa.post.repository.BoardJpaRepository;
import ussum.homepage.infra.jpa.post.repository.PostFileJpaRepository;
import ussum.homepage.infra.jpa.post.repository.PostJpaRepository;
import ussum.homepage.infra.jpa.postlike.repository.PostReactionJpaRepository;
import ussum.homepage.infra.jpa.reaction.repository.PostCommentReactionJpaRepository;
import ussum.homepage.infra.jpa.reaction.repository.PostReplyCommentReactionJpaRepository;
import ussum.homepage.infra.jpa.user.entity.UserEntity;
import ussum.homepage.infra.jpa.user.repository.UserJpaRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({QuerydslConfig.class, PostMapper.class})
@ActiveProfiles("test")
@DisplayName("게시글 저장소 테스트")
class PostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private JPAQueryFactory queryFactory;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private PostJpaRepository postJpaRepository;
    @Autowired
    private PostFileJpaRepository postFileJpaRepository;
    @Autowired
    private PostReactionJpaRepository postReactionJpaRepository;
    @Autowired
    private PostCommentReactionJpaRepository postCommentReactionJpaRepository;
    @Autowired
    private PostReplyCommentReactionJpaRepository postReplyCommentReactionJpaRepository;
    @Autowired
    private BoardJpaRepository boardJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private PostReplyCommentJpaRepository postReplyCommentJpaRepository;
    @Autowired
    private PostCommentJpaRepository postCommentJpaRepository;
    @Autowired
    private EntityManager em;

    private PostRepositoryImpl postRepository;
    private TestDataBuilder testData;

    @BeforeEach
    void setUp() {
        postRepository = new PostRepositoryImpl(
                postJpaRepository, postFileJpaRepository, postReactionJpaRepository,
                postCommentReactionJpaRepository, postReplyCommentReactionJpaRepository,
                boardJpaRepository, userJpaRepository, postMapper, queryFactory,
                postReplyCommentJpaRepository, postCommentJpaRepository, em
        );
        testData = new TestDataBuilder(entityManager);
    }

    @AfterEach
    void cleanup() {
        entityManager.clear();
        postJpaRepository.deleteAll();
        boardJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }

    @Nested
    @DisplayName("기본 게시글 검색 테스트")
    class BasicSearchTest {

        @Test
        @DisplayName("전체 게시글을 상태별로 정렬하여 조회한다")
        void searchAllPostsOrderedByStatus() {
            // given
            TestData data = testData.createBasicTestData();
            Pageable pageable = PageRequest.of(0, 10);

            // when
            Page<Post> result = postRepository.searchAllByBoardIdAndGroupCodeAndMemberCode(
                    data.getBoardId(), null, null, null, pageable);

            // then
            assertAll(
                    () -> assertThat(result.getContent())
                            .hasSize(3)
                            .extracting("status")
                            .containsExactly(
                                    Status.EMERGENCY_NOTICE.getStringStatus(),
                                    Status.NEW.getStringStatus(),
                                    Status.GENERAL.getStringStatus()
                            ),
                    () -> assertThat(result.getContent())
                            .extracting("title")
                            .containsExactly("Emergency Post", "New Post", "General Post"),
                    () -> assertThat(result.getTotalElements()).isEqualTo(3)
            );
        }

        @Test
        @DisplayName("존재하지 않는 게시판 ID로 검색하면 빈 결과를 반환한다")
        void searchWithInvalidBoardId() {
            // given
            testData.createBasicTestData();
            Pageable pageable = PageRequest.of(0, 10);
            Long invalidBoardId = 999L;

            // when
            Page<Post> result = postRepository.searchAllByBoardIdAndGroupCodeAndMemberCode(
                    invalidBoardId, null, null, null, pageable);

            // then
            assertThat(result.getContent()).isEmpty();
        }
    }

    @Nested
    @DisplayName("검색어 필터링 테스트")
    class SearchQueryTest {

        @Test
        @DisplayName("제목에 검색어가 포함된 게시글만 조회한다")
        void searchByTitleKeyword() {
            // given
            TestData data = testData.createBasicTestData();
            Pageable pageable = PageRequest.of(0, 10);
            String query = "Emergency";

            // when
            Page<Post> result = postRepository.searchAllByBoardIdAndGroupCodeAndMemberCode(
                    data.getBoardId(), query, null, null, pageable);

            // then
            assertAll(
                    () -> assertThat(result.getContent())
                            .hasSize(1)
                            .extracting("title")
                            .containsExactly("Emergency Post"),
                    () -> assertThat(result.getTotalElements()).isEqualTo(1)
            );
        }

        @Test
        @DisplayName("검색어가 null이면 전체 게시글을 조회한다")
        void searchWithNullQuery() {
            // given
            TestData data = testData.createBasicTestData();
            Pageable pageable = PageRequest.of(0, 10);

            // when
            Page<Post> result = postRepository.searchAllByBoardIdAndGroupCodeAndMemberCode(
                    data.getBoardId(), null, null, null, pageable);

            // then
            assertThat(result.getTotalElements()).isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("그룹코드 필터링 테스트")
    class GroupCodeFilterTest {

        @Test
        @DisplayName("특정 그룹의 게시글만 조회한다")
        void searchByGroupCode() {
            // given
            TestData data = testData.createBasicTestData();
            Pageable pageable = PageRequest.of(0, 10);

            // when
            Page<Post> result = postRepository.searchAllByBoardIdAndGroupCodeAndMemberCode(
                    data.getBoardId(), null, GroupCode.STUDENT_UNION, null, pageable);

            // then
            assertAll(
                    () -> assertThat(result.getContent())
                            .allMatch(post -> data.getStudentUnionUserId().equals(post.getUserId())),
                    () -> assertThat(result.getContent())
                            .extracting("title")
                            .containsOnly("Emergency Post", "New Post")
            );
        }
    }

    @Nested
    @DisplayName("페이징 처리 테스트")
    class PagingTest {

        @Test
        @DisplayName("페이지 크기만큼 게시글을 조회한다")
        void searchWithPaging() {
            // given
            TestData data = testData.createBasicTestData();
            Pageable pageable = PageRequest.of(0, 2);

            // when
            Page<Post> result = postRepository.searchAllByBoardIdAndGroupCodeAndMemberCode(
                    data.getBoardId(), null, null, null, pageable);

            // then
            assertAll(
                    () -> assertThat(result.getContent()).hasSize(2),
                    () -> assertThat(result.getTotalElements()).isEqualTo(3),
                    () -> assertThat(result.getTotalPages()).isEqualTo(2)
            );
        }

        @Test
        @DisplayName("페이지 크기가 0이면 빈 결과를 반환한다")
        void searchWithZeroPageSize() {
            // given
            TestData data = testData.createBasicTestData();
            Pageable pageable = PageRequest.of(0, 0);

            // when
            Page<Post> result = postRepository.searchAllByBoardIdAndGroupCodeAndMemberCode(
                    data.getBoardId(), null, null, null, pageable);

            // then
            assertThat(result.getContent()).isEmpty();
        }
    }

    private static class TestDataBuilder {
        private final TestEntityManager entityManager;

        public TestDataBuilder(TestEntityManager entityManager) {
            this.entityManager = entityManager;
        }

        public TestData createBasicTestData() {
            // 게시판 생성
            BoardEntity board = entityManager.persist(
                    BoardEntity.builder()
                            .boardCode(BoardCode.NOTICE)
                            .name("Notice Board")
                            .build());

            // 그룹 생성
            GroupEntity studentUnion = entityManager.persist(
                    GroupEntity.builder()
                            .groupCode(GroupCode.STUDENT_UNION)
                            .name("Student Council")
                            .build());

            GroupEntity clubUnion = entityManager.persist(
                    GroupEntity.builder()
                            .groupCode(GroupCode.CLUB_UNION)
                            .name("Club Union")
                            .build());

            // 사용자 생성
            UserEntity user1 = entityManager.persist(
                    UserEntity.builder()
                            .name("User 1")
                            .studentId("20230001")
                            .accountId("user1")
                            .password("password1")
                            .build());

            UserEntity user2 = entityManager.persist(
                    UserEntity.builder()
                            .name("User 2")
                            .studentId("20230002")
                            .accountId("user2")
                            .password("password2")
                            .build());

            // 멤버 생성
            entityManager.persist(
                    MemberEntity.builder()
                            .isAdmin(false)
                            .memberCode(MemberCode.STUDENT_UNION)
                            .userEntity(user1)
                            .groupEntity(studentUnion)
                            .build());

            entityManager.persist(
                    MemberEntity.builder()
                            .isAdmin(false)
                            .memberCode(MemberCode.CLUB_UNION)
                            .userEntity(user2)
                            .groupEntity(clubUnion)
                            .build());

            // 게시글 생성
            LocalDateTime now = LocalDateTime.now();

            entityManager.persist(
                    PostEntity.builder()
                            .title("Emergency Post")
                            .content("Emergency content")
                            .status(Status.EMERGENCY_NOTICE)
                            .userEntity(user1)
                            .boardEntity(board)
                            .build());

            entityManager.persist(
                    PostEntity.builder()
                            .title("New Post")
                            .content("New content")
                            .status(Status.NEW)
                            .userEntity(user1)
                            .boardEntity(board)
                            .build());

            entityManager.persist(
                    PostEntity.builder()
                            .title("General Post")
                            .content("General content")
                            .status(Status.GENERAL)
                            .userEntity(user2)
                            .boardEntity(board)
                            .build());

            entityManager.flush();
            entityManager.clear();

            return new TestData(board.getId(), user1.getId(), user2.getId());
        }
    }

    private static class TestData {
        private final Long boardId;
        private final Long studentUnionUserId;
        private final Long clubUnionUserId;

        public TestData(Long boardId, Long studentUnionUserId, Long clubUnionUserId) {
            this.boardId = boardId;
            this.studentUnionUserId = studentUnionUserId;
            this.clubUnionUserId = clubUnionUserId;
        }

        public Long getBoardId() {
            return boardId;
        }

        public Long getStudentUnionUserId() {
            return studentUnionUserId;
        }

        public Long getClubUnionUserId() {
            return clubUnionUserId;
        }
    }
}