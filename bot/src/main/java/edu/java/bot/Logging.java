package edu.java.bot;

import com.pengrad.telegrambot.model.Update;

public class Logging {
    public void log(Update update) {
        String username =
            update.message().from().username() != null ? update.message().from().username() : "User Not found";
        System.out.println("----------------------------------");
        System.out.println("username:" + username);
        System.out.println("message:" + update.message().text());
    }
}
