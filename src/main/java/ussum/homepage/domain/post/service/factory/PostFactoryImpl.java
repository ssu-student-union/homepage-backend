package ussum.homepage.domain.post.service.factory;

import org.springframework.stereotype.Component;
import ussum.homepage.application.post.service.dto.request.GeneralPostCreateRequest;
import ussum.homepage.application.post.service.dto.request.PostCreateRequest;
import ussum.homepage.application.post.service.dto.request.RightsPostCreateRequest;

@Component
public class PostFactoryImpl implements PostFactory {

    @Override
    public PostCreateRequest convert(String boardCode, PostCreateRequest request) {

        return switch (boardCode) {
            case "인권신고게시판" -> {
                RightsPostCreateRequest rightsPostCreateRequest = (RightsPostCreateRequest) request;
                yield RightsPostCreateRequest.builder()
                        .title(rightsPostCreateRequest.getTitle())
                        .content(rightsPostCreateRequest.getContent())
                        .category(rightsPostCreateRequest.getCategory())
                        .thumbNailImage(rightsPostCreateRequest.getThumbNailImage())
                        .isNotice(rightsPostCreateRequest.isNotice())
                        .postFileList(rightsPostCreateRequest.getPostFileList())
                        .rightsDetailList(rightsPostCreateRequest.getRightsDetailList())
                        .build();
            }
            default -> GeneralPostCreateRequest.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .category(request.getCategory())
                    .thumbNailImage(request.getThumbNailImage())
                    .isNotice(request.isNotice())
                    .postFileList(request.getPostFileList())
                    .build();
        };
    }
}
