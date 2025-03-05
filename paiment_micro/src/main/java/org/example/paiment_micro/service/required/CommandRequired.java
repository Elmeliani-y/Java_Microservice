package org.example.paiment_micro.service.required;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "command-microservice")
public interface CommandRequired {
    @GetMapping("/commands/ref/{ref}")
    Object findByRef(@PathVariable("ref") String ref);
}
