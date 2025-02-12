package org.example.command_micro.service.facade;

import org.example.command_micro.bean.Commande;

public interface CommandService {
    public int save(Commande commande);
    public void deleteByRef(String ref);
    public Commande findByRef(String ref);
    public int update(String ref, Commande commande);
}
