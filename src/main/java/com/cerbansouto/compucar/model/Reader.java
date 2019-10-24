package com.cerbansouto.compucar.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "reader")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reader {

    @Id
    @Column(length = 100, unique = true, nullable = false)
    private String code;

    @Column
    private String brand;

    @Column
    private int batteryLife;

    @Column
    private int batteryUsed;

    @JsonProperty(value = "workshopCode")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn //(name = "workshopCode", referencedColumnName = "code")
    private Workshop workshop;

    @JsonIgnore
    @Column
    private boolean deleted;

    public Reader() { }

    @JsonCreator
    public Reader(String code) {
        this.code = code;
    }
}
