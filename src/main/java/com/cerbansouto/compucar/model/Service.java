package com.cerbansouto.compucar.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(exclude = "diagnoses")
@Entity
@Table(name = "service")
public class Service implements Serializable {

    @Id
    @Column(length = 100)
    private String code;

    @Column
    private Date date;

    @Column
    private int serviceTime;

    @Column
    private float cost;

    @JsonProperty(value = "clientCode")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Client client;

    @JsonProperty(value = "mechanicCode")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Mechanic mechanic;

    @JsonProperty(value = "readerCode")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Reader reader;

    @JsonProperty(value = "workshopCode")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Workshop workshop;

    @Transient
    private List<ServiceEvent> events = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "service")
    private Set<EventDiagnosis> diagnoses = new HashSet<>();

    @JsonIgnore
    @Column
    private boolean deleted;
}
