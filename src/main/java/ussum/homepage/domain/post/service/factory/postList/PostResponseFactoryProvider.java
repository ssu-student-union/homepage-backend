package ussum.homepage.domain.post.service.factory.postList;

import java.util.HashMap;
import java.util.Map;

public class PostResponseFactoryProvider {
    private static final Map<String, PostListResponseFactory> factoryMap = new HashMap<>();

    static {
        factoryMap.put("공지사항게시판", new NoticePostResponseFactory());
        factoryMap.put("분실물게시판", new LostPostResponseFactory());
        factoryMap.put("제휴게시판", new PartnerPostResponseFactory());
        factoryMap.put("감사기구게시판", new AuditPostResponseFactory());
        factoryMap.put("청원게시판", new PetitionPostResponseFactory());
        factoryMap.put("자료집게시판", new DataPostResponseFactory());
        factoryMap.put("건의게시판", new SuggestionPostResponseFactory());
        factoryMap.put("인권신고게시판", new RightsPostResponseFactory());
    }

    public static PostListResponseFactory getFactory(String boardName) {
        PostListResponseFactory factory = factoryMap.get(boardName);
        if (factory == null) {
            throw new IllegalArgumentException("Unknown board type: " + boardName);
        }
        return factory;
    }
}
