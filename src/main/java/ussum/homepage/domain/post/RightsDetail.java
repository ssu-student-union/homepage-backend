package ussum.homepage.domain.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ussum.homepage.infra.jpa.post.entity.RightsDetailEntity.PersonType;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RightsDetail {
    private Long id;
    private String name;
    private String phoneNumber;
    private String studentId;
    private String major;
    private PersonType personType;
    private Long postId;

    public static RightsDetail of(Long id,
                          String name, String phoneNumber,String studentId,String major,PersonType personType,Long postId) {
        return new RightsDetail(id, name, phoneNumber,studentId, major, personType, postId);
    }
}
