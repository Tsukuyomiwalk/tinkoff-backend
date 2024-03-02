package edu.java.clients.overflow;

import edu.java.clients.AbstractWebClient;
import edu.java.clients.dtos.QuestionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StackOverFlowClient extends AbstractWebClient {
    private final WebClient stackOverflowWebClient;

    public Mono<QuestionInfo> getQuestionInfo(long questionId) {
        return getInfo(
            QuestionInfo.class,
            String.format("/questions/%d?site=stackoverflow", questionId)
        );
    }

    @Override
    protected WebClient getWebClient() {
        return stackOverflowWebClient;
    }
}
