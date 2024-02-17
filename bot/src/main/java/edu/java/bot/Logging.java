package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import java.util.logging.Logger;

public class Logging {
    public void log(Update update) {
        String username =
            update.message().from().username() != null ? update.message().from().username() : "User Not found";
        Logger.getLogger("----------------------------------");
        Logger.getLogger("username:" + username);
        Logger.getLogger("message:" + update.message().text());
    }
}
