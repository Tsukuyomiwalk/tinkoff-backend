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
import java.util.List;

public class Bot {
    private static final List<AbstractCommand> CMD =
        List.of(new Start(), new CmdList(), new Help(), new Track(), new Untrack());

    public void startBot(TelegramBot bot) {
        BotCommand[] cmds = CMD.stream().map(abstractCommand ->
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

    private static SendMessage commandBotHandler(Update upd) {
        Long user = upd.message().from().id();
        for (AbstractCommand command : CMD) {
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
