package org.example.command_micro.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaListenerConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerConfig.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "commande-group");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        
        // Configure JsonDeserializer
        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>(
            Object.class, 
            objectMapper
        );
        jsonDeserializer.addTrustedPackages(
            "org.example.command_micro.ws.Dto", 
            "org.example.shared.dto", 
            "java.util", 
            "java.lang"
        );
        
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        
        factory.setConsumerFactory(consumerFactory());
        
        // Configure manual acknowledgment
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        
        // Configure error handling with DefaultErrorHandler
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
            // No recovery callback
            null, 
            // Fixed backoff strategy: 1 second interval, 2 retry attempts
            new FixedBackOff(1000L, 2)
        );
        
        // Optional: Add specific exception handling
        errorHandler.addNotRetryableExceptions(
            IllegalArgumentException.class, 
            IllegalStateException.class
        );
        
        factory.setCommonErrorHandler(errorHandler);
        
        // Logging for error tracking
        logger.info("Kafka Listener Container Factory configured");
        
        return factory;
    }
}
