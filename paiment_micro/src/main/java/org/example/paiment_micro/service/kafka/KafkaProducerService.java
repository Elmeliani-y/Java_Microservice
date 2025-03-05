package org.example.paiment_micro.service.kafka;

import org.example.paiment_micro.ws.dto.PaiementDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, PaiementDto> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, PaiementDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaiement(PaiementDto paiementDto) {
        kafkaTemplate.send("paiment-topic", paiementDto);
    }
}
