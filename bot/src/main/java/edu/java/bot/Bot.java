package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.commands.AbstractCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Bot {
    private final List<AbstractCommand> cmd;

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
            try {
                if (command.isCommand(upd)) {
                    return command.handler(upd);
                }
            } catch (NullPointerException e) {
                return new SendMessage(user,
                    "Формат таких сообщений я не поддерживаю, нажмите /help для просмтора возможных команд");
            }
        }
        return new SendMessage(user, "Такой команды я не знаю, нажмите /help для просмтора возможных команд");
    }
}
