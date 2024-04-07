package edu.java.bot.configuration;

import edu.java.bot.controller.dto.requests.UpdateUrlRequests;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfig {
    private final ApplicationConfig applicationConfig;

    @Value(value = "${spring.kafka.bootstrap-servers}") private String bootstrapAddress;
    @Value(value = "${spring.kafka.consumer.group-id}") private String groupId;
    @Value(value = "${spring.kafka.consumer.auto-offset-reset}") private String autoOffsetReset;

    @Bean
    public NewTopic mainTopic() {
        return TopicBuilder.name(applicationConfig.topic().name())
            .partitions(applicationConfig.topic().partitions())
            .replicas(applicationConfig.topic().replicas())
            .build();
    }

    @Bean
    public NewTopic deadTopic() {
        return TopicBuilder.name(applicationConfig.deadTopic().name())
            .partitions(applicationConfig.deadTopic().partitions())
            .replicas(applicationConfig.deadTopic().replicas())
            .build();
    }

    @Bean
    public ConsumerFactory<String, UpdateUrlRequests> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        var jsonDeserializer = new JsonDeserializer<>(UpdateUrlRequests.class);
        jsonDeserializer.setRemoveTypeHeaders(true);
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UpdateUrlRequests> kafkaListenerContainerFactory(
        ConsumerFactory<String, UpdateUrlRequests> consumerFactory
    ) {
        var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, UpdateUrlRequests>();
        containerFactory.setConcurrency(1);
        containerFactory.setConsumerFactory(consumerFactory);

        return containerFactory;
    }

    @Bean
    public ProducerFactory<String, UpdateUrlRequests> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, UpdateUrlRequests> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<String, UpdateUrlRequests> kafkaTemplate(
        ProducerFactory<String, UpdateUrlRequests> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }

}
