package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext api = SpringApplication.run(BotApplication.class, args);
        Bot botApp = new Bot();
        ApplicationConfig config = api.getBean(ApplicationConfig.class);
        TelegramBot bot = new TelegramBot(config.telegramToken());
        botApp.startBot(bot);
    }
}
