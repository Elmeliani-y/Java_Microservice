package org.example.paiment_micro.service.kafka;

import org.example.paiment_micro.ws.dto.CommandeDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, CommandeDto> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, CommandeDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentEvent(CommandeDto commandDto) {
        kafkaTemplate.send("payment-topic", commandDto);
        System.out.println(" Kafka Event Sent: " + commandDto);
    }
}
