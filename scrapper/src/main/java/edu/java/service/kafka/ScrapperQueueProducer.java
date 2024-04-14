package edu.java.service.kafka;

import edu.java.clients.bot.Requests.UpdatesRequests;
import edu.java.configuration.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.use-queue", havingValue = "true")
public class ScrapperQueueProducer implements BotUpdateSender {

    private final KafkaTemplate<String, UpdatesRequests> kafkaTemplate;
    private final ApplicationConfig applicationConfig;

    @Override
    public void update(UpdatesRequests requests) {

        kafkaTemplate.send(applicationConfig.topic().name(), requests);
    }

}
