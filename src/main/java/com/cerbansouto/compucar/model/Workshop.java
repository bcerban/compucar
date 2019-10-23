package com.cerbansouto.compucar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "workshop")
public class Workshop implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String code;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String city;

    @JsonIgnore
    @Column
    private boolean deleted;
}
