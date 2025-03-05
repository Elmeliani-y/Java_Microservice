package org.example.command_micro.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.command_micro.bean.Commande;
import org.example.command_micro.dao.CommandeDao;
import org.example.command_micro.ws.Dto.CommandeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumer {

    private final CommandeDao commandeDao;
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private static final String TOPIC = "payment-topic";
    private static final String GROUP_ID = "commande-group";

    @Autowired
    private ObjectMapper objectMapper;

    public KafkaConsumer(CommandeDao commandeDao) {
        this.commandeDao = commandeDao;
    }

    @KafkaListener(
        topics = TOPIC, 
        groupId = GROUP_ID,
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeCommande(@Payload String message, Acknowledgment acknowledgment) {
        try {
            // Deserialize JSON to CommandeDto
            CommandeDto commande = objectMapper.readValue(message, CommandeDto.class);
            
            // Process the received commande
            processCommande(commande);
            
            // Manually acknowledge the message
            acknowledgment.acknowledge();
            
            logger.info("Received and processed Commande: {}", commande);
        } catch (IOException e) {
            logger.error("Error deserializing Commande message", e);
            // Handle deserialization error (you might want to send to a dead letter queue)
        } catch (Exception e) {
            logger.error("Error processing Commande", e);
            // Handle processing error
        }
    }

    private void processCommande(CommandeDto commande) {
        try {
            Commande commandeEntity = commandeDao.findByRef(commande.getRef());
            if (commandeEntity != null) {
                commandeEntity.setTotalPaye(commande.getTotalPaye());
                commandeDao.save(commandeEntity);
                logger.info("Order Updated: {} | New Total Paid: {}", commande.getRef(), commande.getTotalPaye());
            } else {
                logger.warn("Order Not Found: {}", commande.getRef());
            }
        } catch (Exception e) {
            logger.error("Error processing message for ref: " + commande.getRef(), e);
        }
    }
}