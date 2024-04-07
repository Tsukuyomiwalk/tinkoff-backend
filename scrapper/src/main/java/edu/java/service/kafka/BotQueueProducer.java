package edu.java.service.kafka;

import edu.java.clients.bot.BotClient;
import edu.java.clients.bot.Requests.UpdatesRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BotQueueProducer implements BotUpdateSender {
    private final BotClient botClient;

    @Override
    public void update(UpdatesRequests requests) {
        botClient.getUpdates(requests).subscribe();
    }
}
