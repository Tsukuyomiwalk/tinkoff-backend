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
        SendMessage response;
        if (!LINKS.containsKey(user)) {
            response = new SendMessage(
                user,
                "Вы не зарегистрированы, введите команду /start, чтобы воспользоваться функционалом"
            );
        } else {
            String[] msg = upd.message().text().strip().split(" ");
            String id;
            try {
                id = msg[1];
                int numId = Integer.parseInt(id);

                if (!isId(numId, user)) {
                    response = new SendMessage(
                        user,
                        "Пожалуйста пришлите валидный номер ссылки в списке, чтобы посмотреть номер напишите /list"
                    );
                } else {
                    LINKS.get(user).remove(numId - 1);
                    response = new SendMessage(user, "Ссылка успешно удалена");
                }
            } catch (IndexOutOfBoundsException e) {
                response =
                    new SendMessage(user, "Вы должны ввести номер по шаблону /untrack <пробел> номер в списке ссылок");
            } catch (NumberFormatException e) {
                response = new SendMessage(user, "Вы ввели не число");
            }
        }
        return response;
    }

    static boolean isId(int id, Long user) {
        return id > 0 && id <= LINKS.get(user).size();
    }
}
