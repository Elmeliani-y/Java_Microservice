package org.example.command_micro.ws.facade;

import org.example.command_micro.bean.Commande;
import org.example.command_micro.service.facade.CommandService;
import org.example.command_micro.ws.Dto.CommandeDto;
import org.example.command_micro.ws.convetor.CommandeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@EnableDiscoveryClient
@RestController
@RequestMapping("api/commande")
public class CommandeWs {
    private final CommandService service;
    private final CommandeConverter converter;

    @Autowired
    public CommandeWs(CommandService service, CommandeConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> save(@RequestBody CommandeDto commandeDto) {
        if (service.findByRef(commandeDto.getRef()) != null) {
            Map<String, String> respose = new HashMap<>();
            respose.put("message", "already exists");
            return new ResponseEntity<>(respose, HttpStatus.CONFLICT);
        }

        service.save(converter.convertToEntity(commandeDto));

        Map<String, String> response = new HashMap<>();
        response.put("message", "Created successfully");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/ref/{ref}")
    public ResponseEntity<CommandeDto> findByRef(@PathVariable String ref) {
        Commande found = service.findByRef(ref);
        if (found == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(converter.convertToDto(found), HttpStatus.OK);
    }

    @PutMapping("/ref/{ref}")
    public ResponseEntity<CommandeDto> update(@PathVariable String ref, @RequestBody CommandeDto commandeDto) {
        Commande found = service.findByRef(ref);
        if (found == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        service.update(ref, converter.convertToEntity(commandeDto));
        return new ResponseEntity<>(commandeDto, HttpStatus.OK);
    }

    @DeleteMapping("/ref/{ref}")
    public ResponseEntity<Void> deleteByRef(@PathVariable String ref) {
        if (service.findByRef(ref) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.deleteByRef(ref);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/")
    public List<CommandeDto> findAll() {
        return service.findAll().stream().map(converter::convertToDto).toList();

    }
}
