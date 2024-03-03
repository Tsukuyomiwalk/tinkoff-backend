package edu.java.bot.controller;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.controller.dto.requests.UpdateUrlRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BotController {
    private final TelegramBot bot;

    public void updates(@RequestBody UpdateUrlRequests request) {
        for (Long tgChatId : request.getTgChatIds()) {
            bot.execute(new SendMessage(String.valueOf(tgChatId), "Link: " + request.getUrl() + " has been updated"));
        }
    }
}
