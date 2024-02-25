package edu.java.scrapper;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.clients.overflow.StackOverFlowClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.test.StepVerifier;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest(httpPort = 8029)
class StackOverFlowClientTest {

    private final StackOverFlowClient stackOverflowClient =
        new StackOverFlowClient(WebClient.create("http://localhost:8029"));

    @Test
    @DisplayName("Test StackOverflow client handling 200 response")
    void testStackOverflowClientWithWireMock() {
        stubFor(get(urlEqualTo("/questions/123456?site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{\"question_id\":123456, \"title\":\"Test question\", \"body\":\"Test body\", \"score\":10, \"view_count\":100, \"creation_date\":\"2024-02-25T12:00:00Z\", \"last_activity_date\":\"2024-02-25T12:30:00Z\"}")));

        StepVerifier.create(stackOverflowClient.getQuestionInfo(123456))
            .expectNextMatches(questionInfo -> {
                assertThat(questionInfo.getQuestionId()).isEqualTo(123456);
                assertThat(questionInfo.getTitle()).isEqualTo("Test question");
                assertThat(questionInfo.getBody()).isEqualTo("Test body");
                assertThat(questionInfo.getScore()).isEqualTo(10);
                assertThat(questionInfo.getViewCount()).isEqualTo(100);
                return true;
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Test StackOverflow client handling 400 error")
    void testStackOverflowClient400Error() {
        stubFor(get(urlEqualTo("/questions/123456?site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(400)));

        StepVerifier.create(stackOverflowClient.getQuestionInfo(123456))
            .expectErrorSatisfies(throwable ->
                assertThat(throwable)
                    .isInstanceOf(ResponseStatusException.class)
                    .satisfies(exception ->
                        assertThat(((ResponseStatusException) exception).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
                    )
            )
            .verify();
    }

}
