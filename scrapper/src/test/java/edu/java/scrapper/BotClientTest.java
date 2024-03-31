package edu.java.scrapper;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.clients.bot.BotClient;
import edu.java.clients.bot.Requests.UpdatesRequests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@WireMockTest(httpPort = 8029)

public class BotClientTest {
    private final BotClient botClient = new BotClient(WebClient.create("http://localhost:8029"), Retry.backoff(3, Duration.ofDays(1)));

    private final UpdatesRequests linkUpdateRequest = new UpdatesRequests(
        1L,
        URI.create("https://www.example.com"),
        "",
        List.of()
    );
    private final static String FAIL_RESPONSE =
        "{\"description\":\"\",\"code\":\"400\",\"exceptionMessage\":\"Invalid request with code 400\"}";

    @Test
    void testUpdateLinkSuccessfully() {
        stubFor(WireMock.post("/updates")
            .willReturn(aResponse().withStatus(200)));

        StepVerifier.create(botClient.getUpdates(linkUpdateRequest))
            .verifyComplete();
    }

    @Test
    void testUpdateLinkApiError() {
        stubFor(WireMock.post("/updates")
            .willReturn(aResponse()
                .withStatus(400)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(FAIL_RESPONSE)));
        StepVerifier.create(botClient.getUpdates(linkUpdateRequest))
            .expectErrorSatisfies(throwable ->
                assertThat(throwable)
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Retries exhausted: 3/3")
            ).verify();
    }

}
