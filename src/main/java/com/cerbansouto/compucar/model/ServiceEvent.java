package com.cerbansouto.compucar.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceEvent implements Serializable {
    private String serviceCode;
    private String name;
    private String payload;

    public ServiceEvent() { }

    public ServiceEvent(String serviceCode, String name, String payload) {
        this.serviceCode = serviceCode;
        this.name = name;
        this.payload = payload;
    }
}
