package com.cerbansouto.compucar.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Data
@Entity
@Table(name = "bean_initialization_report")
public class BeanInitializationReport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Date applicationInitializationDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private List<BeanInitializationTrace> traces;

    public BeanInitializationReport() {
        this.applicationInitializationDate = new Date();
        this.traces = new ArrayList<>();
    }

    public void addTrace(BeanInitializationTrace trace) {
        this.traces.add(trace);
    }
}
