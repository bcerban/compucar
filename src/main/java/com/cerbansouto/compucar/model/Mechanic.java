package com.cerbansouto.compucar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "mechanic")
public class Mechanic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, nullable = false)
    private long number;

    @Column
    private String name;

    @Column
    private String phoneNumber;

    @Column
    private Date hiringDate;

    @JsonIgnore
    @Column
    private boolean deleted;

}
