//package ussum.homepage.application.post.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import ussum.homepage.application.post.service.BoardService;
//import ussum.homepage.application.post.service.dto.BoardListResponse;
//import ussum.homepage.application.post.service.dto.BoardResponse;
//import ussum.homepage.application.post.service.dto.request.BoardCreateRequest;
//import ussum.homepage.application.post.service.dto.request.BoardUpdateRequest;
//import ussum.homepage.global.ApiResponse;
//
//@RequiredArgsConstructor
//@RequestMapping("/boards")
//@RestController
//public class BoardController {
//    private final BoardService boardService;
//    @GetMapping
//    public ApiResponse<BoardListResponse> getBoardList(@RequestParam(name = "page") int page, @RequestParam(name = "take") int take) {
//        BoardListResponse board = boardService.getBoardList(page, take);
//        return ApiResponse.onSuccess(board);
//    }
//    @PostMapping
//    public ApiResponse<?> createBoard(@RequestBody BoardCreateRequest boardCreateRequest) {
//        boardService.createBoard(boardCreateRequest);
//        return ApiResponse.onSuccess(null);
//    }
//    @GetMapping("/{boardCode}")
//    public ApiResponse<BoardResponse> getBoardList(@PathVariable(name = "boardCode")String boardCode) {
//        BoardResponse board = boardService.getBoard(boardCode);
//        return ApiResponse.onSuccess(board);
//    }
//    @PatchMapping("/{boardCode}")
//    public ApiResponse<BoardResponse> editBoard(@PathVariable(name = "boardCode")String boardCode,@RequestBody BoardUpdateRequest boardUpdateRequest) {
//        BoardResponse board = boardService.editBoard(boardCode, boardUpdateRequest);
//        return ApiResponse.onSuccess(board);
//    }
//    @DeleteMapping("/{boardCode}")
//    public ApiResponse<BoardResponse> deleteBoard(@PathVariable(name = "boardCode")String boardCode) {
//        boardService.deleteBoard(boardCode);
//        return ApiResponse.onSuccess(null);
//    }
//
//}
