package ussum.homepage.domain.acl.service;


import ussum.homepage.infra.jpa.acl.entity.TargetGroup;
import ussum.homepage.infra.jpa.acl.entity.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomACL {
    String boardCode() default "";
    String action() default "";
    boolean allowAnonymous() default true;
    BoardPermission[] boardPermissions() default {};
    TargetGroup[] targetGroups() default {};
    Type type() default Type.ALLOW;
    ussum.homepage.infra.jpa.acl.entity.Target target() default ussum.homepage.infra.jpa.acl.entity.Target.USER;
}