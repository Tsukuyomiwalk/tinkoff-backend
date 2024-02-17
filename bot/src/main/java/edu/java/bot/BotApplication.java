package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.Commands.AbstractCommand;
import edu.java.bot.Commands.CmdList;
import edu.java.bot.Commands.Help;
import edu.java.bot.Commands.Start;
import edu.java.bot.Commands.Track;
import edu.java.bot.Commands.Untrack;
import edu.java.bot.configuration.ApplicationConfig;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {

    // загушка для работы track untrack и list
    private final List<AbstractCommand> cmd =
        List.of(new Start(), new CmdList(), new Help(), new Track(), new Untrack());

    public static void main(String[] args) {
        ConfigurableApplicationContext api = SpringApplication.run(BotApplication.class, args);
        BotApplication botApplication = new BotApplication();
        ApplicationConfig config = api.getBean(ApplicationConfig.class);
        TelegramBot bot = new TelegramBot(config.telegramToken());
        botApplication.startBot(bot);
    }

    public void startBot(TelegramBot bot) {
        BotCommand[] cmds = cmd.stream().map(abstractCommand ->
            new BotCommand(abstractCommand.commandName(), abstractCommand.purpose())).toArray(BotCommand[]::new);
        bot.execute(new SetMyCommands(cmds));
        Logging logging = new Logging();
        bot.setUpdatesListener(list -> {
            for (Update update : list) {
                logging.log(update);
                SendMessage sendMessage = commandBotHandler(update);
                bot.execute(sendMessage);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private SendMessage commandBotHandler(Update upd) {
        Long user = upd.message().from().id();
        for (AbstractCommand command : cmd) {
            if (command.isCommand(upd)) {
                return command.handler(upd);
            }
        }
        return new SendMessage(user, "Такой команды я не знаю, нажмите /help для просмтора возможных команд");
    }
}
