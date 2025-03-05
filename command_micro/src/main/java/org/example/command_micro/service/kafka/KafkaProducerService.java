package org.example.command_micro.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.command_micro.ws.Dto.CommandeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC = "commande-topic";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendCommande(CommandeDto commande) {
        try {
            // Convert DTO to JSON using ObjectMapper
            String jsonCommande = objectMapper.writeValueAsString(commande);
            
            // Create Kafka message with headers
            Message<String> message = MessageBuilder
                .withPayload(jsonCommande)
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .setHeader(KafkaHeaders.KEY, commande.getId().toString())
                .build();

            // Send message
            kafkaTemplate.send(message);
            
            logger.info("Sent Commande to Kafka: {}", commande);
        } catch (Exception e) {
            logger.error("Error sending Commande to Kafka", e);
        }
    }
}
