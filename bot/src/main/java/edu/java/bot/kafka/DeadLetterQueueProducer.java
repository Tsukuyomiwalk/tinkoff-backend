package edu.java.bot.kafka;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.controller.dto.requests.UpdateUrlRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeadLetterQueueProducer {
    private final ApplicationConfig applicationConfig;
    private final KafkaTemplate<String, UpdateUrlRequests> kafkaTemplate;

    public void sendToDlq(UpdateUrlRequests update) {
        kafkaTemplate.send(applicationConfig.deadTopic().name(), update);
    }
}
