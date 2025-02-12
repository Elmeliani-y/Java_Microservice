package org.example.paiment_micro.ws.converter;

import org.example.paiment_micro.bean.Paiment;
import org.example.paiment_micro.ws.dto.PaimentDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PaimentConverter {
    public PaimentDto convertToDto(Paiment paiment) {
        PaimentDto paimentDto = new PaimentDto();
        BeanUtils.copyProperties(paiment, paimentDto);
        return paimentDto;
    }
    public Paiment convertToEntity(PaimentDto paimentDto) {
        Paiment paiment = new Paiment();
        BeanUtils.copyProperties(paimentDto, paiment);
        return paiment;
    }
}
