package org.example.paiment_micro.service.facade;

import org.example.paiment_micro.bean.Paiment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PaimentService {
    int save (Paiment paiment);
    Paiment findByCode(String code);
    void deleteByCode(String code);
    void deleteByCommandeRef(String commandeRef);
    Paiment findByCommandeRef(String commandeRef);
    List<Paiment> findAll();

}
