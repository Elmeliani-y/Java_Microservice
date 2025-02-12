package org.example.command_micro.service.impl;

import jakarta.transaction.Transactional;
import org.example.command_micro.bean.Commande;
import org.example.command_micro.dao.CommandeDao;
import org.example.command_micro.service.facade.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeImpl implements CommandService {
    @Autowired
    private CommandeDao commandeDao ;
    @Override
    public int save(Commande commande) {
        Commande ref = findByRef(commande.getRef());
        if (ref != null) {
            return -1 ;
        }
        commandeDao.save(commande);
        return 1 ;
    }
    @Transactional
    @Override
    public void deleteByRef(String ref) {
        commandeDao.deleteByRef(ref);
    }

    @Override
    public Commande findByRef(String ref) {
        return commandeDao.findByRef(ref);
    }
    @Transactional
    @Override
    public int update(String ref, Commande commande) {
        Commande found = findByRef(ref);
        if (found == null) {
            return -1   ;
        }
        found.setTotalPaye(commande.getTotalPaye());
        commandeDao.save(found);
        return 1 ;
    }
    public List<Commande> findAll() {
        return commandeDao.findAll();
    }
}
