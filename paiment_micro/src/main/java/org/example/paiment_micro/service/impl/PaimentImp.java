package org.example.paiment_micro.service.impl;

import jakarta.transaction.Transactional;
import org.example.paiment_micro.bean.Paiment;
import org.example.paiment_micro.dao.PaimentDao;
import org.example.paiment_micro.service.facade.PaimentService;
import org.example.paiment_micro.service.required.CommandRequired;
import org.example.paiment_micro.ws.dto.CommandDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaimentImp implements PaimentService {

    private final PaimentDao paimentDao;
    private final CommandRequired commandRequired;
    @Autowired

    public PaimentImp(PaimentDao paimentDao, CommandRequired commandRequired) {
        this.paimentDao = paimentDao;
        this.commandRequired = commandRequired;
    }


    @Override
    public int save(Paiment paiment) {
        Paiment found = paimentDao.findByCode(paiment.getCode());
        if (found != null) {
            return -1 ;
        }
        CommandDto response = commandRequired.findCommandeByRef(paiment.getCommandeRef());
        if (response == null) {
            return -2;
        }
        var calc = response.getTotalPaye()+paiment.getMontant() ;
        if(Double.compare(calc,response.getTotal())>0){
            return -3;
        };
        response.setTotalPaye(response.getTotalPaye() + paiment.getMontant());
        CommandDto update = commandRequired.update(paiment.getCommandeRef(), response);
        if (update == null) {
            return -4;
        }
        paimentDao.save(paiment);
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
        List<Paiment> collect = paimentDao.findAll();
        if (collect == null) {
            return null;
        }
        return collect ;
    }
}
