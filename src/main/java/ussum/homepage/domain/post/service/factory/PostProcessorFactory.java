package ussum.homepage.domain.post.service.factory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import ussum.homepage.domain.post.service.processor.PostProcessor;

@Component
public class PostProcessorFactory {

    private final Map<String, PostProcessor> processorMap;

    // Spring에서 PostProcessor를 구현한 모든 빈을 주입받아 Map으로 변환
    public PostProcessorFactory(List<PostProcessor> processors) {
        this.processorMap = processors.stream()
                .collect(Collectors.toMap(PostProcessor::getBoardType, processor -> processor));
    }

    public PostProcessor getProcessor(String boardType) {
        PostProcessor processor = processorMap.get(boardType.toUpperCase());
        if (processor == null) {
            throw new IllegalArgumentException("Unknown board type: " + boardType);
        }
        return processor;
    }
}
