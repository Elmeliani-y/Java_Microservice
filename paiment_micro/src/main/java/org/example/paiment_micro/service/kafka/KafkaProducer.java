package org.example.paiment_micro.service.kafka;

import org.example.paiment_micro.ws.dto.CommandDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, CommandDto> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, CommandDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentEvent(CommandDto commandDto) {
        kafkaTemplate.send("payment-topic", commandDto);
        System.out.println(" Kafka Event Sent: " + commandDto);
    }
}
