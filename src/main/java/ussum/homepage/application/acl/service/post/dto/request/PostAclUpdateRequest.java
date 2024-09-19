//package ussum.homepage.application.acl.service.post.dto.request;
//
//import ussum.homepage.domain.acl.PostAcl;
//
//public record PostAclUpdateRequest(
//        String target,
//        String type,
//        String action
//) {
//    public PostAcl toDomain(Long postAclId, Long postId) {
//        return PostAcl.of(
//                postAclId,
//                target,
//                type,
//                action,
//                null,
//                postId
//        );
//    }
//}
//
