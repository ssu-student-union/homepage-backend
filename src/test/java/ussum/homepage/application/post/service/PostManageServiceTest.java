package ussum.homepage.application.post.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.BoardReader;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.domain.post.service.factory.BoardFactory;
import ussum.homepage.domain.post.service.factory.NoticeBoardImpl;
import ussum.homepage.domain.post.service.factory.postList.NoticePostResponseFactory;
import ussum.homepage.domain.post.service.factory.postList.PostResponseFactoryProvider;
import ussum.homepage.domain.postlike.service.PostReactionReader;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.application.post.service.dto.response.postList.NoticePostResponse;
import ussum.homepage.application.post.service.dto.response.postList.PostListRes;
import ussum.homepage.infra.jpa.post.entity.Category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class PostManageServiceTest {

    @InjectMocks
    private PostManageService postManageService;

    @Mock private BoardReader boardReader;
    @Mock private PostReader postReader;
    @Mock private PostReactionReader postReactionReader;
    @Mock private UserReader userReader;

    private NoticePostResponseFactory noticePostResponseFactory;
    private NoticeBoardImpl noticeBoardImpl;

    @BeforeEach
    void setUp() {
        try (MockedStatic<BoardFactory> mockedBoardFactory = mockStatic(BoardFactory.class)) {
            mockedBoardFactory.when(() -> BoardFactory.createBoard(eq("공지사항게시판"), anyLong()))
                    .thenReturn(noticeBoardImpl);
        }

        try (MockedStatic<PostResponseFactoryProvider> mockedFactoryProvider = mockStatic(PostResponseFactoryProvider.class)) {
            mockedFactoryProvider.when(() -> PostResponseFactoryProvider.getFactory("공지사항게시판"))
                    .thenReturn(noticePostResponseFactory);
        }
    }


    private static Stream<Arguments> providePostData() {
        return Stream.of(
                // 공지사항 카테고리
                Arguments.of(Category.EMERGENCY.getStringCategoryCode(), "새로운", true),
                Arguments.of(Category.STUDENT_COUNCIL.getStringCategoryCode(), "진행중", false),

                // 분실물 카테고리
                Arguments.of(Category.LOST_STATUS.getStringCategoryCode(), "완료", false),
                Arguments.of(Category.LOST_REPORT.getStringCategoryCode(), "접수", false),

                // 제휴안내 카테고리
                Arguments.of(Category.MEDICAL.getStringCategoryCode(), "새로운", false),
                Arguments.of(Category.CULTURE.getStringCategoryCode(), "진행중", false),

                // 청원 카테고리
                Arguments.of(Category.IN_PROGRESS.getStringCategoryCode(), "진행중", false),
                Arguments.of(Category.COMPLETED.getStringCategoryCode(), "완료", false),

                // 감사기구 카테고리
                Arguments.of(Category.AUDIT_PLAN.getStringCategoryCode(), "새로운", false),
                Arguments.of(Category.AUDIT_RESULT.getStringCategoryCode(), "완료", false),

                // 단과대 카테고리
                Arguments.of(Category.IT_SCHOOL.getStringCategoryCode(), "새로운", false),
                Arguments.of(Category.BUSINESS_SCHOOL.getStringCategoryCode(), "완료", false)
        );
    }

    @ParameterizedTest
    @MethodSource("providePostData")
    void getPostList_ShouldReturnCorrectResponse(String category, String status, boolean expectedIsEmergency) {
        // Given
        int page = 0;
        int take = 10;
        String boardCode = "공지사항게시판";

        Board board = TestDataFactory.createBoard(boardCode, 1L);
        Post post = TestDataFactory.createPost(1L, "Test Title", "Test Content", category, status);
        User user = TestDataFactory.createUser(1L, "Test User");

        Page<Post> postPage = new PageImpl<>(List.of(post));

        when(boardReader.getBoardWithBoardCode(boardCode)).thenReturn(board);
        when(postReader.getPostListByBoardIdAndGroupCodeAndMemberCode(eq(1L), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(postPage);
        when(userReader.getUserWithId(1L)).thenReturn(user);

        // When
        PostListRes<?> result = postManageService.getPostList(1L, boardCode, page, take, null, null, null);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.postListResDto())
                .isNotNull()
                .hasSize(1);

        NoticePostResponse noticeResponse = (NoticePostResponse) result.postListResDto().get(0);
        assertThat(noticeResponse)
                .satisfies(response -> {
                    assertThat(response.getPostId()).isEqualTo(1L);
                    assertThat(response.getTitle()).isEqualTo("Test Title");
                    assertThat(response.getContent()).isEqualTo("Test Content");
                    assertThat(response.getCategory()).isEqualTo(category);
                    assertThat(response.getStatus()).isEqualTo(status);
                    assertThat(response.getAuthor()).isEqualTo("Test User");
                    assertThat(response.getIsEmergency()).isEqualTo(expectedIsEmergency);
                });

        assertThat(result.pageInfo())
                .isNotNull()
                .satisfies(pageInfo -> {
                    assertThat(pageInfo.pageNum()).isEqualTo(0);
                    assertThat(pageInfo.pageSize()).isEqualTo(1);
                    assertThat(pageInfo.totalElements()).isEqualTo(1);
                });
    }

    @Test
    void getPostList_WithEmptyResult_ShouldReturnEmptyList() {
        // Given
        String boardCode = "공지사항게시판";
        Board board = TestDataFactory.createBoard(boardCode, 1L);
        Page<Post> emptyPage = new PageImpl<>(List.of());

        when(boardReader.getBoardWithBoardCode(boardCode)).thenReturn(board);
        when(postReader.getPostListByBoardIdAndGroupCodeAndMemberCode(eq(1L), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(emptyPage);

        // When
        PostListRes<?> result = postManageService.getPostList(1L, boardCode, 1, 1, null,null, null);

        // Then
        assertThat(result.postListResDto()).isEmpty();
        assertThat(result.pageInfo().totalElements()).isZero();
    }

    @Test
    void getPostList_WithInvalidBoardCode_ShouldThrowException() {
        // Given
        String invalidBoardCode = "InvalidBoard";
        when(boardReader.getBoardWithBoardCode(invalidBoardCode)).thenThrow(new IllegalArgumentException("Invalid board code"));

        // When & Then
        assertThatThrownBy(() -> postManageService.getPostList(1L, null, 1, 1, null,null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid board code");
    }

    @Test
    void getPostList_WithPagination_ShouldReturnCorrectPage() {
        // Given
        int page = 1;
        int take = 5;
        String boardCode = "공지사항게시판";

        Board board = TestDataFactory.createBoard(boardCode, 1L);
        List<Post> posts = TestDataFactory.createMultiplePosts(10);
        Page<Post> postPage = new PageImpl<>(posts.subList(5, 10), PageRequest.of(page, take), 10);

        when(boardReader.getBoardWithBoardCode(boardCode)).thenReturn(board);
        when(postReader.getPostListByBoardIdAndGroupCodeAndMemberCode(eq(1L), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(postPage);
        when(userReader.getUserWithId(anyLong())).thenReturn(TestDataFactory.createUser(1L, "Test User"));

        // When
        PostListRes<?> result = postManageService.getPostList(1L, boardCode, page, take, null, null, null);

        // Then
        assertThat(result.postListResDto()).hasSize(5);
        assertThat(result.pageInfo())
                .satisfies(pageInfo -> {
                    assertThat(pageInfo.pageNum()).isEqualTo(1);
                    assertThat(pageInfo.pageSize()).isEqualTo(5);
                    assertThat(pageInfo.totalElements()).isEqualTo(10);
                    assertThat(pageInfo.totalPages()).isEqualTo(2);
                });
    }

    @Test
    void getPostList_WithNullCategory_ShouldNotThrowException() {
        // Given
        String boardCode = "공지사항게시판";
        Board board = TestDataFactory.createBoard(boardCode, 1L);
        Post post = TestDataFactory.createPost(1L, "Test Title", "Test Content", null, "Active");
        Page<Post> postPage = new PageImpl<>(List.of(post));

        when(boardReader.getBoardWithBoardCode(boardCode)).thenReturn(board);
        when(postReader.getPostListByBoardIdAndGroupCodeAndMemberCode(eq(1L), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(postPage);
        when(userReader.getUserWithId(1L)).thenReturn(TestDataFactory.createUser(1L, "Test User"));

        // When & Then
        assertThatCode(() -> postManageService.getPostList(1L, boardCode, 1, 1, null, null, null))
                .doesNotThrowAnyException();
    }
}

class TestDataFactory {
    static Board createBoard(String name, Long id) {
        Board board = mock(Board.class);
        when(board.getName()).thenReturn(name);
        when(board.getId()).thenReturn(id);
        return board;
    }

    static Post createPost(Long id, String title, String content, String category, String status) {
        Post post = mock(Post.class);
        when(post.getId()).thenReturn(id);
        when(post.getTitle()).thenReturn(title);
        when(post.getContent()).thenReturn(content);
        when(post.getCreatedAt()).thenReturn(LocalDateTime.now().toString());
        when(post.getCategory()).thenReturn(category);
        when(post.getThumbnailImage()).thenReturn("thumbnail.jpg");
        when(post.getStatus()).thenReturn(status);
        when(post.getUserId()).thenReturn(1L);
        return post;
    }

    static User createUser(Long id, String name) {
        User user = mock(User.class);
        when(user.getName()).thenReturn(name);
        return user;
    }
    static List<Post> createMultiplePosts(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createPost((long) i, "Title " + i, "Content " + i,
                        Category.values()[i % Category.values().length].getStringCategoryCode(), "Active"))
                .collect(Collectors.toList());
    }
}