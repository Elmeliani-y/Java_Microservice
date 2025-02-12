package org.example.paiment_micro.dao;

import org.example.paiment_micro.bean.Paiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaimentDao extends JpaRepository<Paiment,Long> {
    Paiment findByCode(String code);
    void deleteByCode(String code);
    void deleteByCommandeRef(String commandeRef);
    Paiment findByCommandeRef(String commandeRef);
}
