package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.net.URI;
import java.net.URISyntaxException;

public class Track implements AbstractCommand {
    @Override
    public String commandName() {
        return "/track";
    }

    @Override
    public String purpose() {
        return "Добавить ссылку в список";
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
        String[] msg = upd.message().text().strip().split(" ");
        String link;
        try {
            link = msg[1];
        } catch (IndexOutOfBoundsException e) {
            return new SendMessage(user, "Вы должны ввести ссылку по шаблону /track <пробел> ссылка");
        }
        if (!isLink(link)) {
            return new SendMessage(user, "Пожалуйста пришлите валидную ссылку");
        }
        LINKS.get(user).add(link);
        return new SendMessage(user, "Ссылка успешно добавлена");
    }

    static boolean isLink(String link) {
        try {
            new URI(link);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
