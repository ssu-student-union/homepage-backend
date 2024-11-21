package ussum.homepage.domain.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ussum.homepage.infra.jpa.post.entity.RightsDetailEntity.PersonType;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RightsDetail {
    private Long rightsDetailId;
    private String name;
    private String phoneNumber;
    private String studentId;
    private String major;
    private String personType;
    private Long postId;

    public static RightsDetail of(Long rightsDetailId,
                          String name, String phoneNumber,String studentId,String major,String personType,Long postId) {
        return new RightsDetail(rightsDetailId, name, phoneNumber,studentId, major, personType, postId);
    }
}
