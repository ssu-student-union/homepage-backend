package ussum.homepage.infra.jpa.acl;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.acl.PostAcl;
import ussum.homepage.infra.jpa.acl.entity.*;
import ussum.homepage.infra.jpa.post.entity.BoardEntity;

@Component
public class PostAclMapper {
    public PostAclEntity toEntity(PostAcl postAcl) {
        return PostAclEntity.of(
                postAcl.getId(),
                TargetGroup.getEnumTargetFromStringTargetGroup(postAcl.getTargetGroup()),
                Target.getEnumTargetFromStringTarget(postAcl.getTarget()),
                Type.getEnumTypeFromStringType(postAcl.getType()),
                Action.getEnumActionFromStringAction(postAcl.getAction()),
                BoardEntity.from(postAcl.getBoardId())
        );
    }

    public PostAcl toDomain(PostAclEntity postAclEntity) {
        return PostAcl.of(
                postAclEntity.getId(),
                TargetGroup.fromEnumOrNull(postAclEntity.getTargetGroup()),
                Target.fromEnumOrNull(postAclEntity.getTarget()),
                Type.fromEnumOrNull(postAclEntity.getType()),
                Action.fromEnumOrNull(postAclEntity.getAction()),
                postAclEntity.getBoardEntity().getId()
        );
    }
}
