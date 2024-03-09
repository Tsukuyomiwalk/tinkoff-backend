package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {
    @Bean
    public TelegramBot telegramBot(ApplicationConfig config) {
        return new TelegramBot(config.telegramToken());
    }
}
