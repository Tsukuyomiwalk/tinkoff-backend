package edu.java.clients;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
public abstract class AbstractWebClient {

    protected <L> Mono<L> getInfo(Class<L> link, String uri) {
        return getWebClient().get()
            .uri(uri)
            .retrieve()
            .onStatus(
                status -> status.is4xxClientError() || status.is5xxServerError(),
                this::handleErrorResponse
            )
            .bodyToMono(link);
    }

    protected Mono<? extends Throwable> handleErrorResponse(ClientResponse response) {
        if (response.statusCode().is4xxClientError()) {
            return Mono.error(new ResponseStatusException(
                response.statusCode(), "Client error: " + response.statusCode()));
        } else if (response.statusCode().is5xxServerError()) {
            return Mono.error(new ResponseStatusException(
                response.statusCode(), "Server error: " + response.statusCode()));
        }
        return Mono.error(new WebClientResponseException(
            "Unknown error", response.statusCode().value(), response.toString(), null, null, null));
    }

    protected abstract WebClient getWebClient();
}
