package com.cerbansouto.compucar.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "workshop")
public class Workshop implements Serializable {

    @Id
    @Column(length = 100, unique = true, nullable = false)
    private String code;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String city;

    @JsonIgnore
    @Column
    private boolean deleted;

    public Workshop() { }

    @JsonCreator
    public Workshop(String code) {
        this.code = code;
    }
}
