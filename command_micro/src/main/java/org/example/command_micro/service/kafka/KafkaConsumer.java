package org.example.command_micro.service.kafka;

import org.example.command_micro.bean.Commande;
import org.example.command_micro.dao.CommandeDao;
import org.example.command_micro.ws.Dto.CommandeDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final CommandeDao commandeDao;

    public KafkaConsumer(CommandeDao commandeDao) {
        this.commandeDao = commandeDao;
    }

    @KafkaListener(topics = "payment-topic", groupId = "commande-group")
    public void updatePaymentStatus(CommandeDto commandDto) {
        Commande commande = commandeDao.findByRef(commandDto.getRef());
        if (commande != null) {
            commande.setTotalPaye(commandDto.getTotalPaye());
            commandeDao.save(commande);
            System.out.println(" Order Updated: " + commandDto.getRef() + " | New Total Paid: " + commandDto.getTotalPaye());
        } else {
            System.out.println(" Order Not Found: " + commandDto.getRef());
        }
    }
}
