package ussum.homepage.application.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ussum.homepage.application.post.service.PostManageService;
import ussum.homepage.application.post.service.dto.request.GeneralPostCreateRequest;
import ussum.homepage.application.post.service.dto.request.PostCreateRequest;
import ussum.homepage.application.post.service.dto.request.PostFileDeleteRequest;
import ussum.homepage.application.post.service.dto.request.PostUpdateRequest;
import ussum.homepage.application.post.service.dto.request.RightsDetailRequest;
import ussum.homepage.application.post.service.dto.response.TopLikedPostListResponse;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;
import ussum.homepage.global.config.custom.BoardRequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Tag(name = "post", description = "게시물 전체 조회(자료집 조회는 별도), 단건 조회, 생성(자료집 생성은 별도), 파일 이미지 업로드, 수정 api")
public class PostManageController {

    private final PostManageService postManageService;
    @Operation(summary = "게시판 별 게시물 리스트 조회 api", description = """
            게시판 별 게시물 리스트 조회 시 필요한 데이터를 조회하는 api 입니다.
            요청으로 boardCode 그리고 queryParam 형식으로 , groupCode(중앙기구, 단과대학생회), memberCode(중앙운영위원회), category(필터링),page(입력 안 할시 첫번째 페이지), take(몇개 가져올지) 값을 넣으면 됩니다.
            공지사항게시판을 사용할때만 groupCode, memberCode에 값을 넣어서 사용하시면 됩니다. 
            나머지 게시판 필터링은 category에 값을 넣고 사용하시면 됩니다.
            
            인권신고게시판의 경우 조회 권한과 글쓰기 권한이 allowedAuthorities, deniedAuthorities에 담겨집니다. 각각 허용하는 권한과 허용하지 않는 권한이 담깁니다.
            학생인권위원회가 조회할시 allowedAuthorities에 ALL_READ가 담겨집니다. 이외의 자치기구/비로그인 deniedAuthorities에 ALL_READ와 WRITE권한이 들어갑니다.
            로그인의 경우 allowedAuthorities의 WRITE만 담기지만 만약 게시물 리스트의 author 필드가 true라면 게시물의 작성자로 리스트중 해당 게시물만 조회가능합니다. 
            """)
    @GetMapping("/{boardCode}/posts")
    public ResponseEntity<ApiResponse<?>> getBoardPostsList(@Parameter(hidden = true) @UserId Long userId,
                                                            @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "take") int take,
                                                            @PathVariable(name = "boardCode") String boardCode, @RequestParam(value = "groupCode", required = false) String groupCode,
                                                            @RequestParam(value = "memberCode",  required = false) String memberCode,
                                                            @RequestParam(value = "suggestionTarget",  required = false) String suggestionTarget,
                                                            @RequestParam(value = "category",  required = false) String category) {

        return ApiResponse.success(postManageService.getPostList(userId, boardCode, page, take, groupCode, memberCode, category, suggestionTarget));
    }

    @Operation(summary = "자료집게시판 게시물 리스트 조회 api", description = """
            자료집게시판 게시물 리스트 조회 시 필요한 데이터를 조회하는 api 입니다.
            queryParam 형식으로 majorCategory(대분류), middleCategory(중분류), subCategory(소분류), page(입력 안 할시 첫번째 페이지), take(몇개 가져올지) 값을 넣으면 됩니다.
            대분류로만 검색하거나 중분류까지만 검색하거나 하면 필요없는 값은 안 보내셔도 됩니다.
            response에서 총학생회칙일때만 isNotice에 true로 가게 했습니다.
            """)
    @GetMapping("data/posts")
    public ResponseEntity<ApiResponse<?>> getDataPostsList(@Parameter(hidden = true) @UserId Long userId,
                                                           @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "take") int take,
                                                           @RequestParam(name = "majorCategory", required = false) String majorCategory, @RequestParam(name = "middleCategory", required = false) String middleCategory,@RequestParam(name = "subCategory", required = false) String subCategory) {

        return ApiResponse.success(postManageService.getDataList(userId, page, take, majorCategory, middleCategory, subCategory));
    }

    @Operation(summary = "게시물 단건 조회 api", description = """
            게시물 단건 조회 시 필요한 데이터를 조회하는 api 입니다.
            요청으로 boardCode와 postId 그리고 form-data 형식으로 key : userId , value: Long 형의 실제 userId 값을 넣으면 됩니다.
            userId를 받는 이유는 비로그인과 로그인 모두 조회는 할 수 있고, 
            이때 로그인 유저가 조회 시 토큰 발급 시 같이 반환 되었던 userId 값을 이용하여, 해당 user가 해당 댓글 혹은 대댓글을 작성하였는지의 여부를 판단하기 위하여 isAuthor 필드를 이용하였습니다.
            userId를 넣고 반환되는 isAuthor 필드가 true 라면 해당 user는 본인이 작성한 게시물 임을 나타냅니다. 
            하지만 isAuthor 필드가 false 라면(비로그인 포함) 해당 user는 본인이 작성한 게시물이 아니기에 수정, 삭제를 막아야 합니다. 
            
            + boardCode가 청원게시판일 때는 중앙운영위원회가 해당 게시물에 댓글을 작성했을 시 postOfficialCommentResponses라는 List 필드 값에 중앙운영위원회가 작성한 공식답변이 담기게 됩니다.
            + 인권신고게시판일때 학생인권위원회가 해당 게시물에 댓글 달았을때에도 "officialCommentList"에 공식답변이 담기게 됩니다.  
            """)
    @GetMapping("/{boardCode}/posts/{postId}")
    public ResponseEntity<ApiResponse<?>> getBoardPost(@PathVariable(name = "boardCode") String boardCode,
                                                       @PathVariable(name = "postId") Long postId,
                                                       @Parameter(hidden = true) @UserId Long userId) {
        return ApiResponse.success(postManageService.getPost(userId, boardCode, postId));
    }

    @Operation(summary = "게시물 생성 api", description = """
            게시물 생성 시 사용하는 api입니다.
            기본적으로 액세스 토큰을 필요로 합니다.
            PathVariable로 노션에 있는 boardCode중 하나를 적습니다.
            만약 boardCode가 "공지사항게시판" 이라면 PostCreateRequest dto의 category필드에 값을 넣지 않습니다.
            그 이유는 공지사항게시판의 카테고리는 글을 쓸 수 있는 작성자의 groupCode와 memberCode에 따라 필터링되어 조회되기 때문입니다.
            
            JSON 형식으로 PostCreateRequest dto를 활용하여 게시글에 적은 값을 전달하면 됩니다.
            PostCreateRequest dto의 List로 되어있는 변수에 들어가는 값 s3에 저장하고 나온 url입니다. 
            이 컨트롤러에 있는 "/board/{boardCode}/files" api를 먼저 사용하여 리턴값으로 전달받는 값을 넣어주면 됩니다. 
            사진이나 파일이 존재하지 않을 시 빈 List로 전달해주시면 됩니다.
            isNotice는 긴급공지 사항을 나타내는 필드로 맞을시 true, 틀릴시 false를 반환하면 됩니다.
            
            인권신고게시판 경우 기존 json에 추가로 relatedPeople 리스트로 전달해주시면 됩니다. 리스트에는 name, major, studentId에 값을 String으로
            넣어서 전달해주시고 personType은 신고자, 피침해자, 침해자중 선택하여 전달해주시면 됩니다.
            """)
    @PostMapping("/{boardCode}/posts")
    public ResponseEntity<ApiResponse<?>> createBoardPost(@Parameter(hidden = true) @UserId Long userId,
                                                          @PathVariable(name = "boardCode") String boardCode,
                                                          @BoardRequestBody PostCreateRequest postCreateRequest){
        return ApiResponse.success(postManageService.createBoardPost(userId, boardCode, postCreateRequest));
    }

    @Operation(summary = "자료집 게시물 생성 api", description = """
            자료집 게시물을 생성하는 api입니다.
            기본적으로 액세스 토큰을 필요로 합니다.
            요청 path에 fileCategory(카테고리 ex.총학생회칙) 값을 문자열 형태로 넣으면 됩니다.
            """)
    @PostMapping("/data/{fileCategory}/post")
    public ResponseEntity<ApiResponse<?>> createDataPost(@Parameter(hidden = true) @UserId Long userId,
                                                         @PathVariable(name = "fileCategory") String fileCategory,
                                                         @RequestBody GeneralPostCreateRequest generalPostCreateRequest) {
        return ApiResponse.success(postManageService.createDataPost(userId, fileCategory, generalPostCreateRequest));
    }
    @Operation(summary = "게시물 생성 시 파일 및 이미지 저장 api", description = """
            게시물 생성 시 파일 및 이미지 저장하는 api입니다.
            우선 이 api는 게시물 생성 api를 사용전 미리 호출하여야 합니다. 기본적으로 반환값이 저장된 파일 및 사진의 url이기 때문에
            이 반환값을 가지고 게시물 생성 api에 있는 dto의 List에 url를 보내주어야 합니다.
            기본적으로 액세스 토큰을 필요로 합니다.
            PathVariable로 노션에 있는 boardCode중 하나를 적습니다.
            s3에 저장할 MultipartFile 형식의 파라미터를 2개 두었습니다.
            첫번째 파라미터는 files라는 key값으로 보내주면 됩니다.
            두번째 파라미터는 images라는 key값으로 보내주면 됩니다.
            이미지와 파일을 경로를 구분하여 저장하기 위해 이러한 방식을 선택했습니다.
            기본적인 api로직은 받은 파라미터를 통해 파일들을 먼저 s3에 저장하고 db에 순차적으로 생성된 id와 url을 하나의 dto로 이를 List로 반환합니다.
            """)
    @PostMapping(value = "/{boardCode}/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> createBoardPostFile(@Parameter(hidden = true) @UserId Long userId,
                                                              @PathVariable(name = "boardCode") String boardCode,
                                                              @RequestPart(value = "files", required = false) MultipartFile[] files,
                                                              @RequestPart(value = "images", required = false) MultipartFile[] images) {
        return ApiResponse.success(postManageService.createBoardPostFile(userId, boardCode, files, images));
    }

    @Operation(summary = "자료집 게시물 생성 시 파일 및 이미지 저장 api", description = """
            자료집 게시물 생성 시 파일 및 이미지 저장하는 api입니다.
            """)
    @PostMapping(value = "/data/files/{fileType}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> createBoardDataPostFile(@Parameter(hidden = true) @UserId Long userId,
                                                              @RequestPart(value = "files", required = false) MultipartFile[] files,
                                                              @PathVariable(name = "fileType") String fileType) {
        return ApiResponse.success(postManageService.createBoardDataPostFile(userId, files, fileType));
    }

    @Operation(summary = "게시물 단건 조회 후 파일 혹은 이미지 삭제 api", description = """
            게시물 단건 조회 후 파일 혹은 이미지 삭제 api입니다.
            삭제하고자하는 파일의 url을 List 형식으로 보내주시면 됩니다.
            """)
    @DeleteMapping("/{boardCode}/files")
    public ResponseEntity<ApiResponse<?>> deleteBoardPostFile(@Parameter(hidden = true) @UserId Long userId,
                                                              @PathVariable(name = "boardCode") String boardCode,
                                                              @RequestBody PostFileDeleteRequest postFileDeleteRequest){
        return ApiResponse.success(postManageService.deleteBoardPostFile(userId, boardCode, postFileDeleteRequest));
    }


    @Operation(summary = "게시물 수정 api", description = """
            게시물을 수정하는 api 입니다. 
            """)
    @PatchMapping("/{boardCode}/posts/{postId}")
    public ResponseEntity<ApiResponse<?>> editBoardPost(@Parameter(hidden = true) @UserId Long userId,
                                                        @PathVariable(name = "boardCode") String boardCode,
                                                        @PathVariable(name = "postId") Long postId,
                                                        @RequestBody PostUpdateRequest postUpdateRequest) {
        return ApiResponse.success(postManageService.editBoardPost(boardCode, postId, postUpdateRequest));
    }
    @Operation(summary = "자료집 게시물 수정 api", description = """
            자료집 게시물을 수정하는 api 입니다. 
            """)
    @PatchMapping("/data/{fileCategory}/posts/{postId}")
    public ResponseEntity<ApiResponse<?>> editBoardDataPost(@Parameter(hidden = true) @UserId Long userId,
                                                        @PathVariable(name = "fileCategory") String fileCategory,
                                                            @PathVariable(name = "postId") Long postId,
                                                        @RequestBody PostUpdateRequest postUpdateRequest) {
        return ApiResponse.success(postManageService.editBoardDatePost(fileCategory, postId, postUpdateRequest));
    }

    @Operation(summary = "게시물 삭제 api", description = """
            게시물을 삭제하는 api 입니다.
            게시물 단건 조회 후 반환된 imageList 혹은 fileList에 있는 url 모두를
            즉, 삭제하고자하는 파일의 url을 List 형식으로 보내주시면 됩니다.
            """)
    @DeleteMapping("/{boardCode}/posts/{postId}")
    public ResponseEntity<ApiResponse<?>> deleteBoardPost(@Parameter(hidden = true) @UserId Long userId,
                                                          @PathVariable(name = "boardCode") String boardCode,
                                                          @PathVariable(name = "postId") Long postId,
                                                          @RequestBody PostFileDeleteRequest postFileDeleteRequest) {
        postManageService.deletePost(boardCode, postId, postFileDeleteRequest);
        return ApiResponse.success(null);
    }

    @Operation(summary = "검색키워드를 활용한 게시판 별 게시물 리스트 조회 api", description = """
            검색키워드를 활용하여 게시판 별 게시물 리스트 조회 시 필요한 데이터를 조회하는 api 입니다.
            요청인자에 q는 검색키워드를 의미하여 필수 값은 아닙니다. 아래 설명은 게시판 별 게시물 리스트 조회와 동일합니다. 
            q(검색 키워드)를 넣고 요청하지 않으면 아무런 값이 반환되지 않습니다. (totalElements는 0)
            요청으로 boardCode 그리고 queryParam 형식으로 , groupCode(중앙기구, 단과대학생회), memberCode(중앙운영위원회), category(필터링), page(입력 안 할시 첫번째 페이지), take(몇개 가져올지) 값을 넣으면 됩니다.
            공지사항게시판을 사용할때만 groupCode, memberCode에 값을 넣어서 사용하시면 됩니다. 
            나머지 게시판 필터링은 category에 값을 넣고 사용하시면 됩니다.
            """)
    @GetMapping("/{boardCode}/posts/search")
    public ResponseEntity<ApiResponse<?>> searchBoardPost(@Parameter(hidden = true) @UserId Long userId,
                                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value = "take") int take,
                                                          @RequestParam(value = "q",required = false) String q,
                                                          @PathVariable(name = "boardCode") String boardCode,
                                                          @RequestParam(value = "groupCode", required = false) String groupCode,
                                                          @RequestParam(value = "memberCode",  required = false) String memberCode,
                                                          @RequestParam(value = "category",  required = false) String category) {
        return ApiResponse.success(postManageService.searchPost(userId, page, take, q, boardCode, groupCode, memberCode, category));
    }

    @Operation(summary = "검색키워드를 활용한 자료집게시판 게시물 리스트 조회 api", description = """
            검색키워드를 활용하여 자료집게시판 게시물 리스트 조회 시 필요한 데이터를 조회하는 api 입니다.
            queryParam 형식으로 majorCategory(대분류), middleCategory(중분류), subCategory(소분류), page(입력 안 할시 첫번째 페이지), take(몇개 가져올지) 값을 넣으면 됩니다.
            대분류로만 검색하거나 중분류까지만 검색하거나 하면 필요없는 값은 안 보내셔도 됩니다.
            response에서 총학생회칙일때만 isNotice에 true로 가게 했습니다.
            """)
    @GetMapping("/data/posts/search")
    public ResponseEntity<ApiResponse<?>> searchDataPostsList(@Parameter(hidden = true) @UserId Long userId,
                                                              @RequestParam(value = "page", defaultValue = "0") int page,
                                                              @RequestParam(value = "take") int take,
                                                              @RequestParam(value = "q",required = false) String q,
                                                              @RequestParam(name = "majorCategory", required = false) String majorCategory,
                                                              @RequestParam(name = "middleCategory", required = false) String middleCategory,
                                                              @RequestParam(name = "subCategory", required = false) String subCategory) {

        return ApiResponse.success(postManageService.searchDataList(userId, page, take, q, majorCategory, middleCategory, subCategory));
    }

    @Operation(summary = "게시판 인기청원 조회 api", description = """
            게시판 인기청원 조회 시 필요한 데이터를 조회하는 api 입니다.
            요청으로 boardCode 그리고 qeury param 형식으로 page, take를 입력하시면 됩니다.
            """)
    @GetMapping("/{boardCode}/posts/top-liked")
    public ResponseEntity<ApiResponse<?>> getTopLikedBoardPostList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "take") int take,
                                                                   @PathVariable(name = "boardCode") String boardCode) {
        TopLikedPostListResponse postList = postManageService.getTopLikedPostList(page, take, boardCode);
        return ApiResponse.success(postList);
    }

    @Operation(summary = "자료집 게시물 단건 조회 api", description = """
            자료집 게시물 단건 조회 시 필요한 데이터를 조회하는 api 입니다.
            form-data 형식으로 key : userId , value: Long 형의 실제 userId 값을 넣으면 됩니다.
            userId를 받는 이유는 비로그인과 로그인 모두 조회는 할 수 있고, 
            이때 로그인 유저가 조회 시 토큰 발급 시 같이 반환 되었던 userId 값을 이용하여, 해당 user가 해당 댓글 혹은 대댓글을 작성하였는지의 여부를 판단하기 위하여 isAuthor 필드를 이용하였습니다.
            userId를 넣고 반환되는 isAuthor 필드가 true 라면 해당 user는 본인이 작성한 게시물 임을 나타냅니다. 
            하지만 isAuthor 필드가 false 라면(비로그인 포함) 해당 user는 본인이 작성한 게시물이 아니기에 수정, 삭제를 막아야 합니다. 
            
            + boardCode가 청원게시판일 때는 중앙운영위원회가 해당 게시물에 댓글을 작성했을 시 postOfficialCommentResponses라는 List 필드 값에 중앙운영위원회가 작성한 공식답변이 담기게 됩니다.
            + 인권신고게시판일때 학생인권위원회가 해당 게시물에 댓글 달았을때에도 "officialCommentList"에 공식답변이 담기게 됩니다.  
            """)
    @GetMapping("/data/posts/{postId}")
    public ResponseEntity<ApiResponse<?>> getBoardPost(@PathVariable(name = "postId") Long postId,
                                                       @Parameter(hidden = true) @UserId Long userId) {
        return ApiResponse.success(postManageService.getDataPost(userId, postId));
    }
}
