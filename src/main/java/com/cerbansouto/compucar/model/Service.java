package com.cerbansouto.compucar.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "service")
public class Service {

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
    @JoinColumn //(name = "client", referencedColumnName = "number")
    private Client client;

    @JsonProperty(value = "mechanicCode")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn //(name = "mechanic", referencedColumnName = "number")
    private Mechanic mechanic;

    @JsonProperty(value = "readerCode")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn //(name = "reader", referencedColumnName = "code")
    private Reader reader;

    @JsonProperty(value = "workshopCode")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn //(name = "workshop", referencedColumnName = "code")
    private Workshop workshop;

    @JsonIgnore
    @Column
    private boolean deleted;
}
