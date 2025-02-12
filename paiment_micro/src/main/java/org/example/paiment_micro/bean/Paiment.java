package org.example.paiment_micro.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Paiment {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private String code ;
    private Double Montant ;
    private String commandeRef ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getMontant() {
        return Montant;
    }

    public void setMontant(Double montant) {
        Montant = montant;
    }

    public String getCommandeRef() {
        return commandeRef;
    }

    public void setCommandeRef(String commandeRef) {
        this.commandeRef = commandeRef;
    }
}
