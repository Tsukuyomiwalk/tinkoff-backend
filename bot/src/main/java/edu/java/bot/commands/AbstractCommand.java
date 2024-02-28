package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public interface AbstractCommand {
    Map<Long, LinkedList<String>> LINKS = new HashMap<>();

    String commandName();

    String purpose();

    SendMessage handler(Update upd);

    default Map<Long, LinkedList<String>> getLinks() {
        return LINKS;
    }

    default void setLinks(HashMap<Long, LinkedList<String>> hm) {
        LINKS.clear();
        LINKS.putAll(hm);
    }

    default boolean isCommand(Update upd) {
        return upd.message().text().trim().startsWith(commandName());
    }
}
