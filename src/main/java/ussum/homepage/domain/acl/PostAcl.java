package ussum.homepage.domain.acl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostAcl {
    private Long id;
    private String targetGroup;
    private String target;
    private String type;
    private String action;
    private Long boardId;
    public static PostAcl of(Long id,
                             String targetGroup,
                              String target,
                              String type,
                              String action,
                              Long boardId){
        return new PostAcl(id, targetGroup, target, type, action, boardId);
    }
}
