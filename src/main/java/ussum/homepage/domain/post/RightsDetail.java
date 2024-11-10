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
    private String studentId;
    private String major;
    private String personType;

    public static RightsDetail of(Long id,
                          String name,String studentId,String major,String personType) {
        return new RightsDetail(id, name, studentId, major, personType);
    }
}
