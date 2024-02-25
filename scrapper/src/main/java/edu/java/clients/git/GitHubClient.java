package edu.java.clients.git;

import edu.java.clients.AbstractWebClient;
import edu.java.clients.dtos.RepositoryInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.RequiredArgsConstructor;

import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GitHubClient extends AbstractWebClient {

    private final WebClient githubWebClient;

    public Mono<RepositoryInfo> getRepositoryInfo(String owner, String repositoryName) {
        return getInfo(RepositoryInfo.class, String.format("/repos/%s/%s", owner, repositoryName));
    }

    @Override
    protected WebClient getWebClient() {
        return githubWebClient;
    }
}
