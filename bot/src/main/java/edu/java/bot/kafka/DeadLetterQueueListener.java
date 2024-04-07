package edu.java.bot.kafka;

import edu.java.bot.controller.dto.requests.UpdateUrlRequests;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeadLetterQueueListener {
    @KafkaListener(topics = "${app.dead-topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenBadUpdates(UpdateUrlRequests update) {
        log.info(update.toString());
    }
}
