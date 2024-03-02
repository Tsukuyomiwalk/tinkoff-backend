package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.LinkedList;

public class Start implements AbstractCommand {
    @Override
    public String commandName() {
        return "/start";
    }

    @Override
    public String purpose() {
        return "Зарегистрировать польззователя";
    }

    @Override
    public SendMessage handler(Update upd) {
        Long user = upd.message().from().id();
        if (LINKS.containsKey(user)) {
            return new SendMessage(
                user,
                "Вы уже зарегистрированы, чтобы ознакомиться с функционалом - напишите (/help)"
            );
        }
        LINKS.put(user, new LinkedList<>());
        return new SendMessage(
            user,
            "Вы успешно зарегистрированы, чтобы ознакомиться с функционалом - напишите (/help)"
        );
    }
}
