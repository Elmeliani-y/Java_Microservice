package org.example.paiment_micro.service.facade;

import org.example.paiment_micro.bean.Paiment;
import org.example.paiment_micro.ws.dto.PaiementDto;

import java.util.List;

public interface PaimentService {
    PaiementDto findByRef(String ref);
    int save(Paiment paiment);
    PaiementDto findByCode(String code);
    void deleteByCode(String code);
    void deleteByCommandeRef(String commandeRef);
    PaiementDto findByCommandeRef(String commandeRef);
    List<PaiementDto> findAll();
}
