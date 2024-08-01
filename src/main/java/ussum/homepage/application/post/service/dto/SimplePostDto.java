//package ussum.homepage.application.post.service.dto;
//
//import com.querydsl.core.annotations.QueryProjection;
//import ussum.homepage.domain.post.Post;
//
//public record SimplePostDto(
//        Post post,
//        Integer like
//) {
//    @QueryProjection
//    public SimplePostDto(Post post, Integer like) {
//        this.post = post;
//        this.like = like;
//    }
//
//    public static SimplePostDto fromPost(Post post, Long likeCount) {
//        return new SimplePostDto(
//                post,
//                Math.toIntExact(likeCount)
//        );
//    }
//}
