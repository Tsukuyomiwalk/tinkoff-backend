package edu.java.clients.bot;

import edu.java.clients.bot.Requests.UpdatesRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
@RequiredArgsConstructor
public class BotClient {
    private final WebClient botClient;
    private final Retry retry;


    public Mono<Void> getUpdates(UpdatesRequests request) {
        return botClient.post()
            .uri("/updates")
            .bodyValue(request)
            .retrieve()
            .onStatus(
                status -> status.is4xxClientError() || status.is5xxServerError(),
                response -> Mono.error(new RuntimeException("Failed to fetch links: " + response.statusCode()))
            )
            .bodyToMono(Void.class).retryWhen(retry);
    }
}
