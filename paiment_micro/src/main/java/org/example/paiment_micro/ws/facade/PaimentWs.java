package org.example.paiment_micro.ws.facade;

import org.example.paiment_micro.bean.Paiment;
import org.example.paiment_micro.service.facade.PaimentService;
import org.example.paiment_micro.ws.dto.PaiementDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paiments")
public class PaimentWs {
    private final PaimentService paimentService;
    private final ModelMapper modelMapper;

    public PaimentWs(PaimentService paimentService, ModelMapper modelMapper) {
        this.paimentService = paimentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public List<PaiementDto> findAll() {
        return paimentService.findAll();
    }

    @PostMapping("/add")
    public int save(@RequestBody PaiementDto paiementDto) {
        return paimentService.save(modelMapper.map(paiementDto, Paiment.class));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<PaiementDto> findByCode(@PathVariable String code) {
        PaiementDto found = paimentService.findByCode(code);
        if (found == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @DeleteMapping("/code/{code}")
    public ResponseEntity<Void> deleteByCode(@PathVariable String code) {
        PaiementDto found = paimentService.findByCode(code);
        if (found == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        paimentService.deleteByCode(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/commandeRef/{commandeRef}")
    public ResponseEntity<Void> deleteByCommandeRef(@PathVariable String commandeRef) {
        PaiementDto found = paimentService.findByCommandeRef(commandeRef);
        if (found == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        paimentService.deleteByCommandeRef(commandeRef);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/commandeRef/{commandeRef}")
    public ResponseEntity<PaiementDto> findByCommandeRef(@PathVariable String commandeRef) {
        PaiementDto found = paimentService.findByCommandeRef(commandeRef);
        if (found == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(found, HttpStatus.OK);
    }
}
