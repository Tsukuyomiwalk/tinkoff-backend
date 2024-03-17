package edu.java.scheluder;


import edu.java.clients.bot.BotClient;
import edu.java.clients.bot.Requests.UpdatesRequests;
import edu.java.clients.git.GitHubClient;
import edu.java.clients.overflow.StackOverFlowClient;
import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Links;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdater;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import static edu.java.scheluder.LinkParser.isGitHubLink;
import static edu.java.scheluder.LinkParser.isStackOverflowLink;

@Slf4j
@EnableScheduling
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.scheduler.enable")
public class LinkUpdaterScheduler {

    private final LinkUpdater linkUpdater;
    private final LinkService linkService;
    private final GitHubClient gitHubClient;
    private final BotClient botClient;
    private final StackOverFlowClient stackOverflowClient;

    @Scheduled(fixedDelayString = "#{@'app-edu.java.configuration.ApplicationConfig'.scheduler.interval}")
    public void upd() {
        log.debug("processing update...");
        List<Links> uncheckedLinks = linkUpdater.getUncheckedLinks();
        for (Links link : uncheckedLinks) {
            List<String> isGithub = isGitHubLink(link.getLink());
            List<String> isStack = isStackOverflowLink(link.getLink());
            if (!isGithub.getFirst().equals("-1")) {
                gitHubClient.getRepositoryInfo(isGithub.getFirst(), isGithub.getLast())
                    .subscribe(repositoryInfo -> {
                        if (isUpdateNeeded(link.getChecked(), repositoryInfo.getUpdatedAt())) {
                            try {
                                botClient.getUpdates(new UpdatesRequests(
                                        Long.parseLong(String.valueOf(link.getId())),
                                        new URI(link.getLink()),
                                        getDescription(link),
                                        linkService.findAllByLink(link.getLink()).stream().map(Chat::getChatId)
                                            .collect(Collectors.toList())
                                    ))
                                    .subscribe();
                            } catch (URISyntaxException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        linkUpdater.refreshUpdateDate(link.getLink(), repositoryInfo.getUpdatedAt());
                    });
            } else if (!isStack.getFirst().equals("-1")) {
                stackOverflowClient.getQuestionInfo(Long.parseLong(isStack.getLast()))
                    .subscribe(questionInfo -> {
                        if (isUpdateNeeded(link.getChecked(), questionInfo.getLastActivityDate())) {
                            try {
                                botClient.getUpdates(new UpdatesRequests(
                                        Long.parseLong(String.valueOf(link.getId())),
                                        new URI(link.getLink()),
                                        getDescription(link),
                                        linkService.findAllByLink(link.getLink()).stream().map(Chat::getChatId)
                                            .collect(Collectors.toList())
                                    ))
                                    .subscribe();
                            } catch (URISyntaxException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        linkUpdater.refreshUpdateDate(link.getLink(), questionInfo.getLastActivityDate());
                    });
            }
        }

    }

    @NotNull private static String getDescription(Links link) {
        return "Есть обновление: " + link.getLink();
    }

    private boolean isUpdateNeeded(OffsetDateTime checked, OffsetDateTime updatedAt) {
        return checked.isBefore(updatedAt);
    }

}
