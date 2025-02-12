package org.example.paiment_micro.ws.facade;

import org.example.paiment_micro.bean.Paiment;
import org.example.paiment_micro.service.facade.PaimentService;
import org.example.paiment_micro.ws.converter.PaimentConverter;
import org.example.paiment_micro.ws.dto.PaimentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/paiment")
@EnableDiscoveryClient
public class PaimentWs {
    @Autowired
    private PaimentService paimentService;
    @Autowired
    private PaimentConverter paimentConverter;

    @GetMapping("/")
    public List<Paiment> findAll() {
        return  paimentService.findAll();
    }
    @PostMapping("/add")
    public int save(@RequestBody PaimentDto paimentDto) {
        return paimentService.save(paimentConverter.convertToEntity(paimentDto));

    }
    @GetMapping("/code/{code}")
    public ResponseEntity<PaimentDto> findByCode(@PathVariable String code) {
        Paiment found = paimentService.findByCode(code);
        if (found == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paimentConverter.convertToDto(found), HttpStatus.OK);
    }
    @DeleteMapping("/code/{code}")
    public ResponseEntity<PaimentDto> deleteByCode(@PathVariable String code) {
        Paiment found = paimentService.findByCode(code);
        if (found == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        paimentService.deleteByCode(code);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    @DeleteMapping("/commandeRef/{commandeRef}")
    public ResponseEntity<PaimentDto> deleteByCommandeRef(@PathVariable String commandeRef) {
        Paiment found = paimentService.findByCommandeRef(commandeRef);
        if (found == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        paimentService.deleteByCommandeRef(commandeRef);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    @GetMapping("/commandeRef/{commandeRef}")
    public ResponseEntity<PaimentDto> findByCommandeRef(@PathVariable String commandeRef) {
        Paiment found = paimentService.findByCommandeRef(commandeRef);
        if (found == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paimentConverter.convertToDto(found), HttpStatus.OK);
    }
}
