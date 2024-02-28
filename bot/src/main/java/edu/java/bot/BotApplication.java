package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.commands.AbstractCommand;
import edu.java.bot.commands.CmdList;
import edu.java.bot.commands.Help;
import edu.java.bot.commands.Start;
import edu.java.bot.commands.Track;
import edu.java.bot.commands.Untrack;
import edu.java.bot.configuration.ApplicationConfig;
import java.util.List;
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
