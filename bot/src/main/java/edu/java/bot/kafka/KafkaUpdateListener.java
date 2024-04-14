package edu.java.bot.kafka;

import edu.java.bot.controller.BotController;
import edu.java.bot.controller.dto.requests.UpdateUrlRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaUpdateListener {
    private final BotController bot;
    private final DeadLetterQueueProducer dlqProducer;

    @KafkaListener(
        topics = "${app.topic.name}",
        groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(UpdateUrlRequests linkUpdateRequest) {
        try {
            bot.updates(linkUpdateRequest);
        } catch (Exception e) {
            dlqProducer.sendToDlq(linkUpdateRequest);
        }
    }
}
