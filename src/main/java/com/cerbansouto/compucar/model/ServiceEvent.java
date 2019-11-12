package com.cerbansouto.compucar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ServiceEvent {
    @JsonIgnore
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
