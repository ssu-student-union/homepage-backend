package ussum.homepage.application.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ussum.homepage.application.post.service.PostManageService;
import ussum.homepage.application.post.service.dto.request.PostCreateRequest;
import ussum.homepage.application.post.service.dto.request.PostUpdateRequest;
import ussum.homepage.application.post.service.dto.request.PostUserRequest;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostManageController {

    private final PostManageService postManageService;
    @Operation(summary = "게시판 별 게시물 리스트 조회 api", description = """
            게시판 별 게시물 리스트 조회 시 필요한 데이터를 조회하는 api 입니다.
            요청으로 boardCode 그리고 queryParam 형식으로 , groupCode(중앙기구, 단과대학생회), memberCode(중앙운영위원회), page(입력 안 할시 첫번째 페이지), take(몇개 가져올지) 값을 넣으면 됩니다.
            공지사항게시판을 사용할때만 groupCode, memberCode에 값을 넣어서 사용하시면 됩니다. 
            """)
    @GetMapping("/{boardCode}/posts")
    public ResponseEntity<ApiResponse<?>> getBoardPostsList(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "take") int take,
                                                            @PathVariable(name = "boardCode") String boardCode, @RequestParam(value = "groupCode", required = false) String groupCode,
                                                            @RequestParam(value = "memberCode", required = false) String memberCode) {

//        PostListResponse postList = postService.getPostList(PageRequest.of(page, take, Sort.by("id").descending()), boardCode);
        return ApiResponse.success(postManageService.getPostList(page, take, boardCode, groupCode, memberCode));
    }

    @Operation(summary = "자료집게시판 게시물 리스트 조회 api", description = """
            자료집게시판 게시물 리스트 조회 시 필요한 데이터를 조회하는 api 입니다.
            queryParam 형식으로 majorCategory(대분류), middleCategory(중분류), subCategory(소분류), page(입력 안 할시 첫번째 페이지), take(몇개 가져올지) 값을 넣으면 됩니다.
            대분류로만 검색하거나 중분류까지만 검색하거나 하면 필요없는 값은 안 보내셔도 됩니다.
            response에서 총학생회칙일때만 isNotice에 true로 가게 했습니다.
            """)
    @GetMapping("data/posts")
    public ResponseEntity<ApiResponse<?>> getDataPostsList(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "take") int take,
                                                            @RequestParam(name = "majorCategory", required = false) String majorCategory, @RequestParam(name = "middleCategory", required = false) String middleCategory,@RequestParam(name = "subCategory", required = false) String subCategory) {

//        PostListResponse postList = postService.getPostList(PageRequest.of(page, take, Sort.by("id").descending()), boardCode);
        return ApiResponse.success(postManageService.getDataList(page, take, majorCategory, middleCategory, subCategory));
    }

    @Operation(summary = "게시물 단건 조회 api", description = """
            게시물 단건 조회 시 필요한 데이터를 조회하는 api 입니다.
            요청으로 boardCode와 postId 그리고 form-data 형식으로 key : userId , value: Long 형의 실제 userId 값을 넣으면 됩니다.
            userId를 받는 이유는 비로그인과 로그인 모두 조회는 할 수 있고, 
            이때 로그인 유저가 조회 시 토큰 발급 시 같이 반환 되었던 userId 값을 이용하여, 해당 user가 해당 댓글 혹은 대댓글을 작성하였는지의 여부를 판단하기 위하여 isAuthor 필드를 이용하였습니다.
            userId를 넣고 반환되는 isAuthor 필드가 true 라면 해당 user는 본인이 작성한 게시물 임을 나타냅니다. 
            하지만 isAuthor 필드가 false 라면(비로그인 포함) 해당 user는 본인이 작성한 게시물이 아니기에 수정, 삭제를 막아야 합니다. 
            
            + boardCode가 청원게시판일 때는 중앙운영위원회가 해당 게시물에 댓글을 작성했을 시 postOfficialCommentResponses라는 List 필드 값에 중앙운영위원회가 작성한 공식답변이 담기게 됩니다.  
            """)
    @GetMapping("/{boardCode}/posts/{postId}")
    public ResponseEntity<ApiResponse<?>> getBoardPost(@PathVariable(name = "boardCode") String boardCode,
                                                       @PathVariable(name = "postId") Long postId,
                                                       @ModelAttribute PostUserRequest postUserRequest) {

        return ApiResponse.success(postManageService.getPost(postUserRequest, boardCode, postId));
    }

    @PostMapping("/{boardCode}/posts")
    public ResponseEntity<ApiResponse<?>> createBoardPost(@UserId Long userId,
                                                          @PathVariable(name = "boardCode") String boardCode,
                                                          @RequestBody PostCreateRequest postCreateRequest){
        return ApiResponse.success(postManageService.createBoardPost(userId, boardCode, postCreateRequest));
    }
    @PostMapping("data/{subCategory}/post")
    public ResponseEntity<ApiResponse<?>> createDataPost(@UserId Long userId,
                                                         @PathVariable(name = "subCategory") String subCategory,
                                                          @RequestBody PostCreateRequest postCreateRequest){
        return ApiResponse.success(postManageService.createDataPost(userId, subCategory, postCreateRequest));
    }

    @PostMapping("/{boardCode}/files")
    public ResponseEntity<ApiResponse<?>> createBoardPostFile(@UserId Long userId,
                                                              @PathVariable(name = "boardCode") String boardCode,
                                                              @RequestPart(value = "files") MultipartFile[] files,
                                                              @RequestParam(value = "type") String typeName) {
        return ApiResponse.success(postManageService.createBoardPostFile(userId, boardCode, files, typeName));
    }

    @PatchMapping("/{boardCode}/posts/{postId}")
    public ResponseEntity<ApiResponse<?>> editBoardPost(@UserId Long userId,
                                                        @PathVariable(name = "boardCode") String boardCode,
                                                        @PathVariable(name = "postId") Long postId,
                                                        @RequestBody PostUpdateRequest postUpdateRequest) {
        return ApiResponse.success(postManageService.editBoardPost(boardCode, postId, postUpdateRequest));
    }

}
