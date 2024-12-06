//package ussum.homepage.domain.post.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
////import ussum.homepage.domain.acl.service.AclModifier;
//import ussum.homepage.domain.post.BoardRepository;
//
//@Service
//@RequiredArgsConstructor
//public class BoardModifier {
//    private final BoardRepository boardRepository;
//    private final BoardReader boardReader;
////    private final AclModifier aclModifier;
//    private final AclReader aclReader;
////    public Board updateBoard(String boardCode, BoardUpdateRequest boardUpdateRequest){
////        return boardRepository.save(boardUpdateRequest.toDomain(
////                boardReader.getBoardWithBoardCode(boardCode).getId()
////        ));
////    }
////    public void deleteBoard(String boardCode){
////
////        List<BoardAcl> boardAcls = aclReader.getBoardAclList(boardReader.getBoardWithBoardCode(boardCode).getId());
////        boardAcls.stream().forEach(boardAcl -> aclModifier.deleteBoardAcl(boardAcl.getId()));
////        boardRepository.delete(boardReader.getBoardWithBoardCode(boardCode));
////    }
//}
