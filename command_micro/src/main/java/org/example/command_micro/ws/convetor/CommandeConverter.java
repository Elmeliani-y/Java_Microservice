package org.example.command_micro.ws.convetor;

import org.example.command_micro.bean.Commande;
import org.example.command_micro.ws.Dto.CommandeDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CommandeConverter {
    public Commande convertToEntity (CommandeDto commandeDto) {
        Commande commande = new Commande();
        BeanUtils.copyProperties(commandeDto , commande);
        return commande ;
    }
    public CommandeDto convertToDto ( Commande commande) {
        CommandeDto commandeDto = new CommandeDto();
        BeanUtils.copyProperties(commande , commandeDto);
        return commandeDto ;
    }
}
