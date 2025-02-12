package org.example.command_micro.dao;

import org.example.command_micro.bean.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeDao extends JpaRepository<Commande, Long> {
    Commande findByRef(String ref);
    void deleteByRef(String ref);

}
