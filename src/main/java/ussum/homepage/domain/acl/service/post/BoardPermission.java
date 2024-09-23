package ussum.homepage.domain.acl.service.post;

import ussum.homepage.infra.jpa.acl.entity.TargetGroup;
import ussum.homepage.infra.jpa.acl.entity.Type;
import ussum.homepage.infra.jpa.post.entity.BoardCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BoardPermission {
    BoardCode boardCode();
    String[] actions();
    TargetGroup[] targetGroups() default {};
    Type type() default Type.ALLOW;
    ussum.homepage.infra.jpa.acl.entity.Target target() default ussum.homepage.infra.jpa.acl.entity.Target.USER;
}
