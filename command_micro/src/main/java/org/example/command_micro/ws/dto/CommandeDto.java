package org.example.command_micro.ws.Dto;

import java.io.Serializable;

public class CommandeDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String ref;
    private Double total;
    private Double totalPaye;

    // Default constructor
    public CommandeDto() {}

    // Parameterized constructor
    public CommandeDto(Long id, String ref, Double total, Double totalPaye) {
        this.id = id;
        this.ref = ref;
        this.total = total;
        this.totalPaye = totalPaye;
    }

    // Getters and Setters
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotalPaye() {
        return totalPaye;
    }

    public void setTotalPaye(Double totalPaye) {
        this.totalPaye = totalPaye;
    }

    // toString method for logging
    @Override
    public String toString() {
        return "CommandeDto{" +
                "id=" + id +
                ", ref='" + ref + '\'' +
                ", total=" + total +
                ", totalPaye=" + totalPaye +
                '}';
    }
}
