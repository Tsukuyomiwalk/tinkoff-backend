package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;


public interface AbstractCommand {

    String commandName();

    String purpose();

    SendMessage handler(Update upd);

    default boolean isCommand(Update upd) {
        return upd.message().text().trim().startsWith(commandName());
    }
}
