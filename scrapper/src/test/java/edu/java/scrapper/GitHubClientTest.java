package edu.java.scrapper;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.clients.git.GitHubClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

import java.time.Duration;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest(httpPort = 8029)
class GitHubClientTest {
    private final GitHubClient githubClient = new GitHubClient(
        WebClient.create("http://localhost:8029"),
        Retry.backoff(3, Duration.ofMillis(1))
    );
    @Test
    @DisplayName("Test GitHub Client Handling 200 response")
    void testGitHubClient200() {
        stubFor(get(urlEqualTo("/repos/test1/test1"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"name\":\"test-repo\", \"description\":\"Test repository\"}")));

        StepVerifier.create(githubClient.getRepositoryInfo("test1", "test1"))
            .expectNextMatches(repositoryInfo -> {
                assertThat(repositoryInfo.getName()).isEqualTo("test-repo");
                assertThat(repositoryInfo.getDescription()).isEqualTo("Test repository");
                return true;
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Test GitHub Client Handling 400 error")
    void testGitHubClient400() {
        stubFor(get(urlEqualTo("/repos/test2/test2"))
            .willReturn(aResponse()
                .withStatus(400)));

        StepVerifier.create(githubClient.getRepositoryInfo("test2", "test2"))
            .expectErrorSatisfies(throwable ->
                assertThat(throwable)
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Retries exhausted: 3/3")
            )
            .verify();
    }
}
