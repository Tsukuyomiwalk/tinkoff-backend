package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.LinkedList;

public class CmdList implements AbstractCommand {
    @Override
    public String commandName() {
        return "/list";
    }

    @Override
    public String purpose() {
        return "Показывает список всех отслеживаемых ссылок";
    }

    @Override
    public SendMessage handler(Update upd) {
        Long user = upd.message().from().id();
        if (!LINKS.containsKey(user)) {
            return new SendMessage(
                user,
                "Вы не зарегистрированы, введите команду /start, чтобы воспользоваться функционалом"
            );
        }

        if (LINKS.get(user).isEmpty()) {
            return new SendMessage(
                user,
                "Список ссылок пуст, чтобы добавить ссылки воспользуйтесь /track <пробел> ссылка"
            );
        }
        StringBuilder st = new StringBuilder();
        LinkedList<String> urls = LINKS.get(user);

        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            st.append(url).append(" -> №").append(i + 1).append('\n');
        }
        return new SendMessage(user, st.toString());
    }

}
