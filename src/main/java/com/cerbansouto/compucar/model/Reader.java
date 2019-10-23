package com.cerbansouto.compucar.model;

import lombok.Data;

@Data
public class Reader {

    private String code;
    private String brand;
    private int batteryLife;
    private int batteryUsed;

    private Workshop workshop;
}
