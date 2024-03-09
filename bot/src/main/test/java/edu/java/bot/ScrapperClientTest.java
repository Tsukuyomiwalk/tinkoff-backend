package edu.java.bot;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.google.gson.Gson;
import edu.java.bot.controller.dto.responses.LinkResponse;
import edu.java.bot.controller.dto.responses.LinksResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;
import java.net.URI;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@WireMockTest(httpPort = 8029)
class ScrapperClientTest {

    private final ScrapperClient scrapperClient = new ScrapperClient(WebClient.create("http://localhost:8029"));

    private final static String FAIL_RESPONSE =
        "{\"description\":\"\",\"code\":\"400\",\"exceptionMessage\":\"Invalid request with code 400\"}";

    @Test
    void registerChatSuccessfully() {
        stubFor(WireMock.post("/tg-chat/1")
            .willReturn(aResponse().withStatus(HttpStatus.OK.value())));

        StepVerifier.create(scrapperClient.register(1L))
            .verifyComplete();
    }

    @Test
    void testUnregisterChatSuccessfully() {
        stubFor(WireMock.delete("/tg-chat/1")
            .willReturn(aResponse().withStatus(HttpStatus.OK.value())));

        StepVerifier.create(scrapperClient.unregister(1L))
            .verifyComplete();
    }

    @Test
    void testGetLinksSuccessfully() {
        LinksResponse linksResponse = new LinksResponse(
            List.of(new LinkResponse(1, URI.create("test"))),
            1
        );
        stubFor(WireMock.get("/links")
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(new Gson().toJson(linksResponse))));

        StepVerifier.create(scrapperClient.getLinks(1L))
            .assertNext(response -> {
                assertFalse(response.getLinks().isEmpty());
                assertThat(response.getSize()).isEqualTo(1);
                assertEquals(response.getLinks().getFirst().getId(), 1);
                assertEquals(response.getLinks().getFirst().getUrl().toString(), "test");
            })
            .verifyComplete();
    }

    @Test
    void testAddLinkSuccessfully() {
        stubFor(WireMock.post("/links")
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"url\":\"https://www.example.com\",\"id\":1}")));

        StepVerifier.create(scrapperClient.addLink(1L, "https://www.example.com"))
            .assertNext(response -> {
                assertEquals(response.id, 1L);
                assertEquals(response.url.toString(), "https://www.example.com");
            })
            .verifyComplete();
    }

    @Test
    void testDeleteLinkSuccessfully() {
        stubFor(WireMock.delete("/links")
            .withRequestBody(equalToJson("{\"url\":\"" + "https://www.example.com" + "\"}"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"url\":\"" + "https://www.example.com" + "\"}")));

        StepVerifier.create(scrapperClient.removeLink(1L, "https://www.example.com"))
            .assertNext(response -> assertThat(response.getUrl().toString()).isEqualTo("https://www.example.com"))
            .verifyComplete();
    }

    @Test
    void testApiError() {
        stubFor(WireMock.post("/tg-chat/1")
            .willReturn(aResponse()
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(FAIL_RESPONSE)));

        StepVerifier.create(scrapperClient.register(1L))
            .expectErrorSatisfies(throwable ->
                assertThat(throwable)
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Telegram operation failed: 400 BAD_REQUEST")
            ).verify();
    }

}
