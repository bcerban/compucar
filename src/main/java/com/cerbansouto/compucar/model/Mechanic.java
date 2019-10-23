package com.cerbansouto.compucar.model;

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
    private long employeeCode;

    @Column
    private String name;

    @Column(unique = true)
    private String phoneNumber;

    @Column
    private Date hiringDate;

}
