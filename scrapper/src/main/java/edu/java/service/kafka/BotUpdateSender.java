package edu.java.service.kafka;

import edu.java.clients.bot.Requests.UpdatesRequests;

public interface BotUpdateSender {

    void update(UpdatesRequests requests);
}
