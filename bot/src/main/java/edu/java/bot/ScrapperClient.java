package edu.java.bot;

import edu.java.bot.controller.dto.requests.DeleteUrlRequests;
import edu.java.bot.controller.dto.requests.UpdateUrlRequests;
import edu.java.bot.controller.dto.responses.LinkResponse;
import edu.java.bot.controller.dto.responses.LinksResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
@RequiredArgsConstructor
public class ScrapperClient {
    private static final String CHAT_HEADER = "Tg-Chat-Id";
    private static final String CHAT_END = "/tg-chat/{id}";
    private static final String LINKS_END = "/links";

    private final Retry retry;

    private final WebClient scrapperWebClient;

    public Mono<Void> register(Long id) {
        return tgHttpOperations(HttpMethod.POST, id);
    }

    public Mono<Void> unregister(Long id) {
        return tgHttpOperations(HttpMethod.DELETE, id);
    }

    public Mono<LinkResponse> addLink(Long chatId, String request) {
        return httpsOperations(
            HttpMethod.POST,
            chatId,
            new UpdateUrlRequests(URI.create(request))
        );
    }

    public Mono<LinkResponse> removeLink(Long chatId, String request) {
        return httpsOperations(
            HttpMethod.DELETE,
            chatId,
            new DeleteUrlRequests(URI.create(request))
        );
    }

    public Mono<LinksResponse> getLinks(Long chatId) {
        return scrapperWebClient.get()
            .uri(LINKS_END)
            .header(CHAT_HEADER, chatId.toString())
            .retrieve()
            .onStatus(
                status -> status.is4xxClientError() || status.is5xxServerError(),
                response -> Mono.error(new RuntimeException("Failed to fetch: " + response.statusCode()))
            )
            .bodyToMono(LinksResponse.class).retryWhen(retry);

    }

    private Mono<LinkResponse> httpsOperations(HttpMethod method, long id, Object requestBody) {
        return scrapperWebClient.method(method)
            .uri(LINKS_END)
            .header(CHAT_HEADER, String.valueOf(id))
            .bodyValue(requestBody)
            .retrieve()
            .onStatus(
                status -> status.is4xxClientError() || status.is5xxServerError(),
                response -> Mono.error(new RuntimeException("Failed to fetch links: " + response.statusCode()))
            )
            .bodyToMono(LinkResponse.class).retryWhen(retry);
    }

    private Mono<Void> tgHttpOperations(HttpMethod method, Long id) {
        return scrapperWebClient.method(method)
            .uri(CHAT_END, id)
            .retrieve()
            .bodyToMono(Void.class)
            .onErrorResume(this::handleError).retryWhen(retry);
    }

    private <T> Mono<T> handleError(Throwable error) {
        if (error instanceof WebClientResponseException ex) {
            if (ex.getStatusCode().is4xxClientError() || ex.getStatusCode().is5xxServerError()) {
                return Mono.error(new RuntimeException("Telegram operation failed: " + ex.getStatusCode()));
            }
        }
        return Mono.error(error);
    }
}
