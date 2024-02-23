package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.HashMap;
import java.util.LinkedList;

public interface AbstractCommand {
    HashMap<Long, LinkedList<String>> LINKS = new HashMap<>();

    String commandName();

    String purpose();

    SendMessage handler(Update upd);

    default HashMap<Long, LinkedList<String>> getLinks() {
        return LINKS;
    }

    default void setLinks(HashMap<Long, LinkedList<String>> hm) {
        LINKS.clear();
        LINKS.putAll(hm);
    }

    default boolean isCommand(Update upd) {
        return upd.message().text().strip().startsWith(commandName());
    }
}
