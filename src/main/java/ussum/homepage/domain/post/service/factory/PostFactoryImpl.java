package ussum.homepage.domain.post.service.factory;

import org.springframework.stereotype.Component;
import ussum.homepage.application.post.service.dto.request.*;

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
            case "건의게시판" ->{
                SuggestionPostCreateRequest suggestionPostCreateRequest = (SuggestionPostCreateRequest) request;
                yield SuggestionPostCreateRequest.builder()
                        .title(suggestionPostCreateRequest.getTitle())
                        .content(suggestionPostCreateRequest.getContent())
                        .category(suggestionPostCreateRequest.getCategory())
                        .thumbNailImage(suggestionPostCreateRequest.getThumbNailImage())
                        .isNotice(suggestionPostCreateRequest.isNotice())
                        .postFileList(suggestionPostCreateRequest.getPostFileList())
                        .suggestionTarget(suggestionPostCreateRequest.getSuggestionTarget())
                        .build();
            }
            case "질의응답게시판" ->{
                QnAPostCreateRequest qnaPostCreateRequest = (QnAPostCreateRequest) request;
                yield QnAPostCreateRequest.builder()
                        .title(qnaPostCreateRequest.getTitle())
                        .content(qnaPostCreateRequest.getContent())
                        .category(qnaPostCreateRequest.getCategory())
                        .thumbNailImage(qnaPostCreateRequest.getThumbNailImage())
                        .isNotice(qnaPostCreateRequest.isNotice())
                        .postFileList(qnaPostCreateRequest.getPostFileList())
                        .qnaMajorCode(qnaPostCreateRequest.getQnaMajorCode())
                        .qnaMemberCode(qnaPostCreateRequest.getQnaMemberCode())
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
