package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class Untrack implements AbstractCommand {
    @Override
    public String commandName() {
        return "/untrack";
    }

    @Override
    public String purpose() {
        return "Удалить ссылку из списка";
    }

    @Override
    public SendMessage handler(Update upd) {
        Long user = upd.message().from().id();
        if (!links.containsKey(user)) {
            return new SendMessage(
                user,
                "Вы не зарегистрированы, введите команду /start, чтобы воспользоваться функционалом"
            );
        }
        String[] msg = upd.message().text().strip().split(" ");
        String id;
        try {
            id = msg[1];
        } catch (IndexOutOfBoundsException e) {
            return new SendMessage(user, "Вы должны ввести ссылку по шаблону /track <пробел> ссылка");
        }
        int numId;
        try {
            numId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return new SendMessage(user, "Вы ввели не число");
        }
        if (!isId(numId, user)) {
            return new SendMessage(
                user,
                "Пожалуйста пришлите валидный номер ссылки в списке, чтобы посмотреть номер напишите /list"
            );
        }
        links.get(user).remove(numId - 1);
        return new SendMessage(user, "Ссылка успешно удалена");
    }

    static boolean isId(int id, Long user) {
        return id > 0 && id <= links.get(user).size();
    }
}
