package com.cerbansouto.compucar.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class Event implements Serializable {
    private String serviceCode;
    private String name;
    private String payload;

    public Event() { }

    public Event(String serviceCode, String name, String payload) {
        this.serviceCode = serviceCode;
        this.name = name;
        this.payload = payload;
    }
}
