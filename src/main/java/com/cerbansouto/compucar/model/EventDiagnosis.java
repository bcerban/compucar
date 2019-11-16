package com.cerbansouto.compucar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "diagnosis")
public class EventDiagnosis implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @JoinColumn(name = "service_code", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Service service;

    private String eventName;

    private String result;
}
