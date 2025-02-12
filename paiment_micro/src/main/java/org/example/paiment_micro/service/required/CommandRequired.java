package org.example.paiment_micro.service.required;

import org.example.paiment_micro.ws.dto.CommandDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CommandRequired {
    private final RestTemplate restTemplate;
    public CommandDto CommandDto;
    private final String commandeBaseUrl;

    public CommandRequired(RestTemplate restTemplate, @Value("${app.api.commande}") String commandeBaseUrl) {
        this.restTemplate = restTemplate;
        this.commandeBaseUrl = commandeBaseUrl;
    }

    public CommandDto findCommandeByRef(String ref) {
        String url = commandeBaseUrl + "/ref/" + ref;
        return restTemplate.getForEntity(url, CommandDto.class).getBody();
    }
    public CommandDto update(String ref , CommandDto commandDto) {
        String url = commandeBaseUrl + "/ref/" + ref;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CommandDto> request = new HttpEntity<>(commandDto, headers);
        return restTemplate.postForEntity(url, request, CommandDto.class).getBody();
    }


}
