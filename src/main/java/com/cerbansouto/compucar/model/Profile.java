package com.cerbansouto.compucar.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Date date;

    @Column
    private String operation;

    @Column
    private long averageResponseTime;

    @Column
    private int numInvocations;
}
