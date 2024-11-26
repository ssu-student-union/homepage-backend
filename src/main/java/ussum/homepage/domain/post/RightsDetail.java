package ussum.homepage.domain.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RightsDetail {
    private String name;
    private String phoneNumber;
    private String studentId;
    private String major;
    private String personType;
    private Long postId;

    public static RightsDetail of(String name, String phoneNumber,String studentId,String major,String personType,Long postId) {
        return new RightsDetail(name, phoneNumber,studentId, major, personType, postId);
    }
}
