package ussum.homepage.application.post.service.dto.response.postSave;

import lombok.Builder;

@Builder
public record PostFileDeleteResponse(
        int s3DeleteCount,
        Long postFileDeleteCount
) {
    public static PostFileDeleteResponse of(int s3DeleteCount, long postFileDeleteCount) {
        return PostFileDeleteResponse.builder()
                .s3DeleteCount(s3DeleteCount)
                .postFileDeleteCount(postFileDeleteCount)
                .build();
    }
}
