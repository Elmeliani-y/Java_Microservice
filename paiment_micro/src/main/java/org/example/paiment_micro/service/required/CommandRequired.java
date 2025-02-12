package org.example.paiment_micro.service.required;

import org.example.paiment_micro.ws.dto.CommandDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "commande-micro", url = "${app.api.commande}")
public interface CommandRequired {

    @GetMapping("/ref/{ref}")
    CommandDto findCommandeByRef(@PathVariable String ref);

    @PutMapping("/ref/{ref}")
    CommandDto update(@PathVariable String ref, @RequestBody CommandDto commandDto);
}
