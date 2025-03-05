package org.example.command_micro.service.facade;

import org.example.command_micro.bean.Commande;
import java.util.List;

public interface CommandService {
    Commande save(Commande commande);
    Commande findByRef(String ref);
    void deleteByRef(String ref);
    List<Commande> findAll();
    Commande update(String ref, Commande commande);
}
