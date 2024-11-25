package ussum.homepage.application.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
import ussum.homepage.infra.jpa.post.entity.BoardCode;
import ussum.homepage.infra.jpa.post.entity.BoardEntity;
import ussum.homepage.infra.jpa.post.entity.PostEntity;
import ussum.homepage.infra.jpa.post.entity.Status;
import ussum.homepage.infra.jpa.post.repository.BoardJpaRepository;
import ussum.homepage.infra.jpa.post.repository.PostFileJpaRepository;
import ussum.homepage.infra.jpa.post.repository.PostJpaRepository;
import ussum.homepage.infra.jpa.postlike.repository.PostReactionJpaRepository;
import ussum.homepage.infra.jpa.reaction.repository.PostCommentReactionJpaRepository;
import ussum.homepage.infra.jpa.reaction.repository.PostReplyCommentReactionJpaRepository;
import ussum.homepage.infra.jpa.user.entity.UserEntity;
import ussum.homepage.infra.jpa.user.repository.UserJpaRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({QuerydslConfig.class, PostMapper.class})
@ActiveProfiles("test")
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

    private BoardEntity boardEntity;
    private UserEntity userEntity1, userEntity2;
    private MemberEntity memberEntity1, memberEntity2;
    private GroupEntity groupEntity1, groupEntity2;
    private PostEntity post1, post2, post3;

    @BeforeEach
    void setUp() {
        postRepository = new PostRepositoryImpl(
                postJpaRepository,
                postFileJpaRepository,
                postReactionJpaRepository,
                postCommentReactionJpaRepository,
                postReplyCommentReactionJpaRepository,
                boardJpaRepository,
                userJpaRepository,
                postMapper,
                queryFactory,
                postReplyCommentJpaRepository,
                postCommentJpaRepository,
                em
        );

        // 게시판 생성
        boardEntity = BoardEntity.of(null, BoardCode.NOTICE, "Notice Board");  // "공지사항" -> "Notice Board"
        boardEntity = entityManager.persist(boardEntity);

        // 그룹 생성
        groupEntity1 = GroupEntity.of(null, GroupCode.STUDENT_UNION, "Student Council");  // "총학생회" -> "Student Council"
        groupEntity2 = GroupEntity.of(null, GroupCode.CLUB_UNION, "Club Union");  // "동아리연합회" -> "Club Union"
        groupEntity1 = entityManager.persist(groupEntity1);
        groupEntity2 = entityManager.persist(groupEntity2);

        // 사용자 생성
        userEntity1 = UserEntity.of(null, "User 1", "20230001", null, null, "user1", "password1", null);
        userEntity2 = UserEntity.of(null, "User 2", "20230002", null, null, "user2", "password2", null);
        userEntity1 = entityManager.persist(userEntity1);
        userEntity2 = entityManager.persist(userEntity2);

        // 멤버 생성
        memberEntity1 = MemberEntity.of(null, false, MemberCode.STUDENT_UNION, null, userEntity1, groupEntity1);
        memberEntity2 = MemberEntity.of(null, false, MemberCode.CLUB_UNION, null, userEntity2, groupEntity2);
        memberEntity1 = entityManager.persist(memberEntity1);
        memberEntity2 = entityManager.persist(memberEntity2);

        // 게시글 생성
        post1 = PostEntity.of(null, "Emergency Post", "Emergency content", 0, null,
                Status.EMERGENCY_NOTICE, LocalDateTime.now(), null, null, userEntity1, boardEntity);

        post2 = PostEntity.of(null, "New Post", "New content", 0, null,
                Status.NEW, LocalDateTime.now(), null, null, userEntity1, boardEntity);

        post3 = PostEntity.of(null, "General Post", "General content", 0, null,
                Status.GENERAL, LocalDateTime.now(), null, null, userEntity2, boardEntity);

        post1 = entityManager.persist(post1);
        post2 = entityManager.persist(post2);
        post3 = entityManager.persist(post3);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("게시글 검색 - 기본 조회")
    void searchAll_BasicTest() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Post> result = postRepository.searchAllByBoardIdAndGroupCodeAndMemberCode(
                boardEntity.getId(), null, null, null, pageable);

        // then
        assertAll(
                () -> assertThat(result.getContent()).hasSize(3),
                () -> assertThat(result.getContent().get(0).getStatus()).isEqualTo(Status.EMERGENCY_NOTICE.getStringStatus()),
                () -> assertThat(result.getContent().get(1).getStatus()).isEqualTo(Status.NEW.getStringStatus()),
                () -> assertThat(result.getContent().get(2).getStatus()).isEqualTo(Status.GENERAL.getStringStatus())
        );
    }

    @Test
    @DisplayName("게시글 검색 - 검색어 필터링")
    void searchAll_WithQueryTest() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        String query = "Emergency";

        // when
        Page<Post> result = postRepository.searchAllByBoardIdAndGroupCodeAndMemberCode(
                boardEntity.getId(), query, null, null, pageable);

        // then
        assertAll(
                () -> assertThat(result.getContent()).hasSize(1),
                () -> assertThat(result.getContent().get(0).getTitle()).contains("Emergency")
        );
    }

    @Test
    @DisplayName("게시글 검색 - 그룹코드 필터링")
    void searchAll_WithGroupCodeTest() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Post> result = postRepository.searchAllByBoardIdAndGroupCodeAndMemberCode(
                boardEntity.getId(), null, GroupCode.STUDENT_UNION, null, pageable);

        // then
        assertThat(result.getContent())
                .allMatch(post -> userEntity1.getId().equals(post.getUserId()));
    }

    @Test
    @DisplayName("게시글 검색 - 멤버코드 필터링")
    void searchAll_WithMemberCodeTest() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Post> result = postRepository.searchAllByBoardIdAndGroupCodeAndMemberCode(
                boardEntity.getId(), null, null, MemberCode.STUDENT_UNION, pageable);

        // then
        assertThat(result.getContent())
                .allMatch(post -> userEntity1.getId().equals(post.getUserId()));
    }

    @Test
    @DisplayName("게시글 검색 - 페이징 테스트")
    void searchAll_PagingTest() {
        // given
        Pageable pageable = PageRequest.of(0, 2);

        // when
        Page<Post> result = postRepository.searchAllByBoardIdAndGroupCodeAndMemberCode(
                boardEntity.getId(), null, null, null, pageable);

        // then
        assertAll(
                () -> assertThat(result.getContent()).hasSize(2),
                () -> assertThat(result.getTotalElements()).isEqualTo(3),
                () -> assertThat(result.getTotalPages()).isEqualTo(2)
        );
    }
}