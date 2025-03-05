package org.example.paiment_micro.service.kafka;

import org.example.paiment_micro.ws.dto.PaiementDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final String TOPIC = "paiment-topic";
    private static final String GROUP_ID = "paiment-group";

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void consumePaiement(PaiementDto paiementDto) {
        // Process the received payment
        System.out.println("Received Payment: " + paiementDto);
    }
}
