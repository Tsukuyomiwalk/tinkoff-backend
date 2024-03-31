package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.ScrapperClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
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
        String[] message = upd.message().text().split(" ");
        return scrapperClient.addLink(user, message[1])
            .thenReturn(new SendMessage(user, "Ссылка успешно добавлена"))
            .onErrorResume(error -> Mono.just(new SendMessage(
                user,
                "Произошла ошибка при добавлении ссылки"
            ))).block();
    }
}
