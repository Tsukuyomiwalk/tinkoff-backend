package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.ScrapperClient;
import edu.java.bot.controller.dto.responses.LinkResponse;
import java.util.LinkedList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CmdList implements AbstractCommand {

    private final ScrapperClient scrapperClient;

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
        return scrapperClient.getLinks(user)
            .flatMap(linksResponse -> {
                if (linksResponse.getLinks().isEmpty()) {
                    return Mono.just(new SendMessage(
                        user,
                        "Список ссылок пуст, чтобы добавить ссылки воспользуйтесь /track <пробел> ссылка"
                    ));
                } else {
                    StringBuilder st = new StringBuilder();
                    LinkedList<LinkResponse> urls = new LinkedList<>(linksResponse.getLinks());

                    for (int i = 0; i < urls.size(); i++) {
                        LinkResponse url = urls.get(i);
                        st.append(url).append(" -> №").append(i + 1).append('\n');
                    }
                    return Mono.just(new SendMessage(user, st.toString()));
                }
            })
            .onErrorResume(error -> Mono.just(new SendMessage(
                user,
                "Произошла ошибка при получении списка отслеживаемых ссылок"
            ))).block();
    }

}
