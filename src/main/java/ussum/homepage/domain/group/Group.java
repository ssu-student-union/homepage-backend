package ussum.homepage.domain.group;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Group {
    private Long id;
    private String groupCode;
    private String name;

    public static Group of(Long id, String groupCode, String name) {
        return Group.builder()
                .id(id)
                .groupCode(groupCode)
                .name(name)
                .build();
    }


}
