//package ussum.homepage.application.acl.service.post.dto.response;
//
//import ussum.homepage.domain.acl.PostAcl;
//
//public record PostAclResponse(Long id,
//                              String target,
//                              String type,
//                              String action) {
//
//    public static PostAclResponse of(PostAcl postAcl){
//        return new PostAclResponse(
//                postAcl.getId(),
//                postAcl.getTarget(),
//                postAcl.getType(),
//                postAcl.getAction()
//        );
//    }
//}
