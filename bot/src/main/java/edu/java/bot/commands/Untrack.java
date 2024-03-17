package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.ScrapperClient;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class Untrack implements AbstractCommand {
    ScrapperClient scrapperClient;

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
        return scrapperClient.unregister(user)
            .thenReturn(new SendMessage(user, "Ссылка успешно удалена"))
            .onErrorResume(error -> Mono.just(new SendMessage(
                user,
                "Произошла ошибка при удалении ссылки"
            ))).block();
    }
}
