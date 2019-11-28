package com.cerbansouto.compucar.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "bean_initialization_trace")
public class BeanInitializationTrace implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Date tracedOn;

    @Column
    private String beanName;

    @Column
    private String beanClassName;

    public BeanInitializationTrace() {
        this.tracedOn = new Date();
    }
}
