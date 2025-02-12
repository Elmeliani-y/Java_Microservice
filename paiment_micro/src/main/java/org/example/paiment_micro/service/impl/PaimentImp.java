package org.example.paiment_micro.service.impl;

import jakarta.transaction.Transactional;
import org.example.paiment_micro.bean.Paiment;
import org.example.paiment_micro.dao.PaimentDao;
import org.example.paiment_micro.service.kafka.KafkaProducer;
import org.example.paiment_micro.service.facade.PaimentService;
import org.example.paiment_micro.service.required.CommandRequired;
import org.example.paiment_micro.ws.dto.CommandDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaimentImp implements PaimentService {

    private final PaimentDao paimentDao;
    private final CommandRequired commandRequired;
    private final KafkaProducer kafkaProducer;

    @Autowired
    public PaimentImp(PaimentDao paimentDao, CommandRequired commandRequired, KafkaProducer kafkaProducer) {
        this.paimentDao = paimentDao;
        this.commandRequired = commandRequired;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public int save(Paiment paiment) {
        Paiment found = paimentDao.findByCode(paiment.getCode());
        if (found != null) return -1;

        CommandDto response = commandRequired.findCommandeByRef(paiment.getCommandeRef());
        if (response == null) return -2;

        double newTotalPaye = response.getTotalPaye() + paiment.getMontant();
        if (newTotalPaye > response.getTotal()) return -3;

        response.setTotalPaye(newTotalPaye);
        paimentDao.save(paiment);
        kafkaProducer.sendPaymentEvent(response);

        return 1;
    }

    @Override
    public Paiment findByCode(String code) {
        return paimentDao.findByCode(code);
    }

    @Transactional
    @Override
    public void deleteByCode(String code) {
        paimentDao.deleteByCode(code);
    }

    @Transactional
    @Override
    public void deleteByCommandeRef(String commandeRef) {
        paimentDao.deleteByCommandeRef(commandeRef);
    }

    @Override
    public Paiment findByCommandeRef(String commandeRef) {
        return paimentDao.findByCommandeRef(commandeRef);
    }

    @Override
    public List<Paiment> findAll() {
        return paimentDao.findAll();
    }
}
