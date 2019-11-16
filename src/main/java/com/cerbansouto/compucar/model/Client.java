package com.cerbansouto.compucar.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "client")
public class Client implements Serializable {

    @Id
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

    public Client() { }

    @JsonCreator
    public Client(Long number) {
        this.number = number;
    }
}
