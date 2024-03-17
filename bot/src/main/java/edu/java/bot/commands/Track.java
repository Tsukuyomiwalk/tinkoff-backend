package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.ScrapperClient;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class Track implements AbstractCommand {
    ScrapperClient scrapperClient;

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
        return scrapperClient.register(user)
            .thenReturn(new SendMessage(user, "Ссылка успешно добавлена"))
            .onErrorResume(error -> Mono.just(new SendMessage(
                user,
                "Произошла ошибка при добавлении ссылки"
            ))).block();
    }
}
