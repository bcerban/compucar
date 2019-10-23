package com.cerbansouto.compucar.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Data
@Entity
@Table(name = "reader")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NaturalId
    @Column(length = 100, unique = true, nullable = false)
    private String code;

    @Column
    private String brand;

    @Column
    private int batteryLife;

    @Column
    private int batteryUsed;

    @Transient
    private String workshopCode;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workshop", referencedColumnName = "code")
    private Workshop workshop;

    @JsonIgnore
    @Column
    private boolean deleted;
}
