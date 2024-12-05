package ussum.homepage.domain.acl.annotation;

import ussum.homepage.infra.jpa.acl.entity.Action;
import ussum.homepage.infra.jpa.acl.entity.Type;
import ussum.homepage.infra.jpa.post.entity.BoardCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 단일 권한 정의 어노테이션
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ACLPermission {
    ussum.homepage.infra.jpa.acl.entity.Target target() default ussum.homepage.infra.jpa.acl.entity.Target.USER;              // 대상
    Type type() default Type.ALLOW;                         // 허용/거부
    Action[] actions() default {};                       // 행동
    int order() default 0;                                  // 우선순위
    String userId() default "";                             // 특정 사용자 ID (target이 SPECIFIC_USER일 때)
    String groupCode() default "";                          // 특정 그룹 코드 (target이 GROUP일 때)
    BoardCode boardCode() default BoardCode.NONE;           // 게시판 코드
}
