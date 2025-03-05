package org.example.command_micro.service.impl;

import jakarta.transaction.Transactional;
import org.example.command_micro.bean.Commande;
import org.example.command_micro.dao.CommandeDao;
import org.example.command_micro.service.facade.CommandService;
import org.example.command_micro.ws.Dto.CommandeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeImpl implements CommandService {

    private final CommandeDao commandeDao;
    private static final Logger logger = LoggerFactory.getLogger(CommandeImpl.class);

    @Autowired
    public CommandeImpl(CommandeDao commandeDao) {
        this.commandeDao = commandeDao;
    }

    @Override
    @Transactional
    public Commande save(Commande commande) {
        Commande existingCommande = findByRef(commande.getRef());
        if (existingCommande != null) {
            logger.warn("Commande with ref {} already exists.", commande.getRef());
            throw new RuntimeException("Commande with reference " + commande.getRef() + " already exists");
        }
        return commandeDao.save(commande);
    }

    @Override
    public Commande findByRef(String ref) {
        return commandeDao.findByRef(ref);
    }

    @Override
    @Transactional
    public void deleteByRef(String ref) {
        Commande commande = findByRef(ref);
        if (commande != null) {
            commandeDao.delete(commande);
            logger.info("Commande deleted: {}", ref);
        }
    }

    @Override
    public List<Commande> findAll() {
        return commandeDao.findAll();
    }

    @Override
    @Transactional
    public Commande update(String ref, Commande commande) {
        Commande existingCommande = findByRef(ref);
        if (existingCommande == null) {
            logger.warn("Commande not found for update: {}", ref);
            throw new RuntimeException("Commande not found with reference: " + ref);
        }
        
        // Update fields
        existingCommande.setTotalPaye(commande.getTotalPaye());
        
        Commande updatedCommande = commandeDao.save(existingCommande);
        logger.info("Commande updated: {} | New Total Paid: {}", ref, commande.getTotalPaye());
        return updatedCommande;
    }

    // Kafka Listener to update the command when payment event is received
    @KafkaListener(topics = "payment-topic", groupId = "commande-group")
    public void updatePaymentStatus(CommandeDto commandDto) {
        try {
            logger.info("Received Kafka Event: {}", commandDto);
            Commande commande = findByRef(commandDto.getRef());
            if (commande != null) {
                commande.setTotalPaye(commandDto.getTotalPaye());
                commandeDao.save(commande);
                logger.info("Commande Updated: {} | New Total Paid: {}", commandDto.getRef(), commandDto.getTotalPaye());
            } else {
                logger.warn("Commande Not Found: {}", commandDto.getRef());
            }
        } catch (Exception e) {
            logger.error("Error processing payment status update", e);
        }
    }
}
