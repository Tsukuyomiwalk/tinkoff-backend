package edu.java.configuration;

import edu.java.jpa.JpaChatService;
import edu.java.jpa.JpaLinkService;
import edu.java.jpa.repository.JpaChatRepo;
import edu.java.jpa.repository.JpaLinkRepo;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public LinkService linkService(
        JpaLinkRepo linkRepository,
        JpaChatRepo chatRepository
    ) {
        return new JpaLinkService(linkRepository, chatRepository);
    }

    @Bean
    public ChatService telegramChatService(
        JpaChatRepo chatRepository
    ) {
        return new JpaChatService(chatRepository);
    }
}
