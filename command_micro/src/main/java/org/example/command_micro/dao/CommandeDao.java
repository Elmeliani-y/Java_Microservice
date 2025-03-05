package org.example.command_micro.dao;

import org.example.command_micro.bean.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommandeDao extends JpaRepository<Commande, Long> {
    Commande findByRef(String ref);

    @Modifying
    @Transactional
    @Query("DELETE FROM Commande c WHERE c.ref = :ref")
    void deleteByRef(@Param("ref") String ref);
}
