package ussum.homepage.domain.post.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ussum.homepage.domain.comment.service.PostCommentReader;
import ussum.homepage.domain.member.service.MemberManager;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostRepository;
import ussum.homepage.domain.postlike.service.PostReactionReader;
import ussum.homepage.infra.jpa.post.entity.Category;
import ussum.homepage.infra.jpa.post.entity.OngoingStatus;
import ussum.homepage.infra.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("<PetitionPostOnGoingStatusProcessor Test>")
@ExtendWith(MockitoExtension.class)
public class PetitionPostOngoingStatusProcessorTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private PostReactionReader postReactionReader;

    @Mock
    private PostCommentReader postCommentReader;

    @Mock
    private MemberManager memberManager;

    @InjectMocks
    private PetitionPostOngoingStatusProcessor petitionPostOngoingStatusProcessor;

    @Test
    @DisplayName("좋아요 수가 99개일 때는 진행중 상태를 유지해야 한다.")
    void testOnLikeCountChanged_InProgressToReceived() {
        // given
        Post mockPost = mock(Post.class);
        Long postId = 1L;
        LocalDateTime createdAt = LocalDateTime.now().minusDays(10);

        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));
        when(mockPost.getOnGoingStatus()).thenReturn(OngoingStatus.IN_PROGRESS.getStringOnGoingStatus());
        when(mockPost.getCreatedAt()).thenReturn(DateUtils.formatHourMinSecToCustomString(createdAt));
        when(postReactionReader.countPostReactionsByType(postId, "like")).thenReturn(99);

        // when
        petitionPostOngoingStatusProcessor.onLikeCountChanged(postId);

        // then
        verify(postRepository, never()).updatePostOngoingStatus(anyLong(), anyString(), any());
        assertThat(mockPost.getOnGoingStatus()).isEqualTo(OngoingStatus.IN_PROGRESS.getStringOnGoingStatus());
    }

    @Test
    @DisplayName("좋아요 수가 100개가 되는 순간 진행중 상태에서 접수완료 상태로 변경되야 한다.")
    void testOnLikeCountChanged_100Likes_InProgressToReceived() {
        // given
        Post mockPost = mock(Post.class);
        Long postId = 1L;
        LocalDateTime createdAt = LocalDateTime.now().minusDays(10);

        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));
        when(mockPost.getOnGoingStatus()).thenReturn(OngoingStatus.IN_PROGRESS.getStringOnGoingStatus());
        when(mockPost.getCreatedAt()).thenReturn(DateUtils.formatHourMinSecToCustomString(createdAt));
        when(mockPost.getId()).thenReturn(postId);
        when(postReactionReader.countPostReactionsByType(postId, "like")).thenReturn(100);

        doAnswer(invocation -> {
            when(mockPost.getOnGoingStatus()).thenReturn(OngoingStatus.RECEIVED.getStringOnGoingStatus());
            return null;
        }).when(postRepository).updatePostOngoingStatus(postId, "접수완료", Category.RECEIVED);

        // when
        petitionPostOngoingStatusProcessor.onLikeCountChanged(postId);

        // then
        verify(postRepository, times(1)).updatePostOngoingStatus(postId, "접수완료", Category.RECEIVED);
        assertThat(mockPost.getOnGoingStatus()).isEqualTo(OngoingStatus.RECEIVED.getStringOnGoingStatus());
    }

}
