package org.example.paiment_micro.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommandeDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String ref;
    private Double totalPaye;
    private Double total;

    // Default constructor
    public CommandeDto() {}

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Double getTotalPaye() {
        return totalPaye;
    }

    public void setTotalPaye(Double totalPaye) {
        this.totalPaye = totalPaye;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
