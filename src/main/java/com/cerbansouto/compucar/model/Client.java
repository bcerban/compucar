package com.cerbansouto.compucar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long number;

    @Column
    private String name;

    @Column(length = 100, unique = true)
    private String email;

    @Column
    private String phone;

    @Column
    private String type;

    @JsonIgnore
    @Column
    private boolean deleted;
}
