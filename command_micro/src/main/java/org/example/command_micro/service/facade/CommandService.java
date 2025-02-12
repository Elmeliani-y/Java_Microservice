package org.example.command_micro.service.facade;

import org.example.command_micro.bean.Commande;

import java.util.List;

public interface CommandService {
    public int save(Commande commande);
    public void deleteByRef(String ref);
    public Commande findByRef(String ref);
    public int update(String ref, Commande commande);

    List<Commande> findAll();
}
