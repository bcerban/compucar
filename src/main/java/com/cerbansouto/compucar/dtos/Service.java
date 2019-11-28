package com.cerbansouto.compucar.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class Service implements Serializable {
    private String code;
    private Event[] events;
    private boolean isReprocessing = false;
}
