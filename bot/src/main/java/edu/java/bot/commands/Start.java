package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.ScrapperClient;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class Start implements AbstractCommand {
    private final ScrapperClient scrapperClient;

    @Override
    public String commandName() {
        return "/start";
    }

    @Override
    public String purpose() {
        return "Зарегистрировать пользователя";
    }

    @Override
    public SendMessage handler(Update upd) {
        Long user = upd.message().from().id();
        return scrapperClient.register(user)
            .then(Mono.defer(() -> scrapperClient.getLinks(user)
                .flatMap(linksResponse -> Mono.just(new SendMessage(
                    user,
                    "Вы успешно зарегистрированы, чтобы ознакомиться с функционалом - напишите (/help)"
                )))
                .onErrorResume(error -> Mono.just(new SendMessage(
                    user,
                    "Произошла ошибка при получении информации о ваших ссылках"
                )))))
            .onErrorResume(error -> Mono.just(new SendMessage(
                user,
                "Произошла ошибка при регистрации пользователя"
            ))).block();
    }
}
