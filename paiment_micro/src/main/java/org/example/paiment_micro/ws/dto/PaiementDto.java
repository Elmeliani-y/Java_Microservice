package org.example.paiment_micro.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementDto implements Serializable {
    private Long id;
    private String ref;
    private Double montant;
    private String description;
}
