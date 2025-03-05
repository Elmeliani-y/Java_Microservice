package org.example.paiment_micro.service.impl;

import org.example.paiment_micro.bean.Paiment;
import org.example.paiment_micro.dao.PaimentDao;
import org.example.paiment_micro.service.facade.PaimentService;
import org.example.paiment_micro.service.required.CommandRequired;
import org.example.paiment_micro.ws.dto.PaiementDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaimentImp implements PaimentService {
    private final PaimentDao paimentDao;
    private final CommandRequired commandRequired;
    private final ModelMapper modelMapper;

    public PaimentImp(PaimentDao paimentDao, CommandRequired commandRequired, ModelMapper modelMapper) {
        this.paimentDao = paimentDao;
        this.commandRequired = commandRequired;
        this.modelMapper = modelMapper;
    }

    @Override
    public PaiementDto findByRef(String ref) {
        Object commande = commandRequired.findByRef(ref);
        // Placeholder mapping
        return new PaiementDto();
    }

    @Override
    @Transactional
    public int save(Paiment paiment) {
        Paiment savedPaiment = paimentDao.save(paiment);
        return savedPaiment.getId().intValue();
    }

    @Override
    public PaiementDto findByCode(String code) {
        Paiment paiment = paimentDao.findByCode(code);
        return modelMapper.map(paiment, PaiementDto.class);
    }

    @Override
    @Transactional
    public void deleteByCode(String code) {
        paimentDao.deleteByCode(code);
    }

    @Override
    @Transactional
    public void deleteByCommandeRef(String commandeRef) {
        paimentDao.deleteByCommandeRef(commandeRef);
    }

    @Override
    public PaiementDto findByCommandeRef(String commandeRef) {
        Paiment paiment = paimentDao.findByCommandeRef(commandeRef);
        return modelMapper.map(paiment, PaiementDto.class);
    }

    @Override
    public List<PaiementDto> findAll() {
        return paimentDao.findAll().stream()
                .map(paiment -> modelMapper.map(paiment, PaiementDto.class))
                .collect(Collectors.toList());
    }
}
