package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class Help implements AbstractCommand {

    @Override
    public String commandName() {
        return "/help";
    }

    @Override
    public String purpose() {
        return "Возможности бота";
    }

    @Override
    public SendMessage handler(Update upd) {
        Long user = upd.message().from().id();
        return new SendMessage(
            user,
            """
                Привет! Я - бот, готовый помочь тебе отслеживать обновления на интересующих тебя веб-ресурсах.\s

                Чтобы начать отслеживать обновления на сайте, треде, репозитории и т. д.
                , просто отправь мне команду /track вместе со ссылкой на ресурс в формате: команда <пробел> ссылка.

                Чтобы прекратить отслеживание обновлений,
                 воспользуйся командой /untrack и укажи номер ссылки из списка уже добавленных в формате:
                  команда <пробел> номер в списке ссылок.

                Также ты можешь запросить полный список ссылок,
                 за которыми ты следишь, просто написав команду /list."""
        );
    }
}
