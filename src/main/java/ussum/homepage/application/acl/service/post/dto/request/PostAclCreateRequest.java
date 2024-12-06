//package ussum.homepage.application.acl.service.post.dto.request;
//
//import ussum.homepage.domain.acl.PostAcl;
//import ussum.homepage.infra.jpa.acl.entity.Action;
//import ussum.homepage.infra.jpa.acl.entity.Target;
//import ussum.homepage.infra.jpa.acl.entity.Type;
//
//public record PostAclCreateRequest(
//        String target,
//        String type,
//        String action
//) {
//    public PostAcl toDomain(Long postId) {
//        return PostAcl.of(
//                null,
//                target,
//                type,
//                action,
//                null,
//                postId);
//    }
//
//}
