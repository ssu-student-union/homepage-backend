package ussum.homepage.domain.acl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ACLRule {
    ACLPermission[] value() default {};                     // 여러 권한 정의 가능
}
