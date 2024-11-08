package ussum.homepage.domain.post.service.factory;

import ussum.homepage.application.post.service.dto.request.GeneralPostCreateRequest;
import ussum.homepage.application.post.service.dto.request.PostCreateRequest;

public class PostFactoryImpl implements PostFactory {

    @Override
    public PostCreateRequest convert(String boardCode, PostCreateRequest request) {
        return switch (boardCode){
            case "인권신고게시판" ->
                GeneralPostCreateRequest.builder()
                        .title(request.getTitle())
                        .content(request.getContent())
                        .isNotice(request.isNotice())
                        .thumbNailImage(request.getThumbNailImage())
                        .postFileList(request.getPostFileList())
                        .build();
            default ->
                    GeneralPostCreateRequest.builder()
                            .title(request.getTitle())
                            .content(request.getContent())
                            .isNotice(request.isNotice())
                            .thumbNailImage(request.getThumbNailImage())
                            .postFileList(request.getPostFileList())
                            .build();
        };
    }
}
