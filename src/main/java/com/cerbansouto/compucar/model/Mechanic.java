package com.cerbansouto.compucar.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "mechanic")
public class Mechanic implements Serializable {

    @Id
    @Column(unique = true, nullable = false)
    private long number;

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private Date startDate;

    @JsonIgnore
    @Column
    private boolean deleted;

    public Mechanic() { }

    @JsonCreator
    public Mechanic(long number) {
        this.number = number;
    }
}
