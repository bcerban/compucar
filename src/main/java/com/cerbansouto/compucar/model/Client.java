package com.cerbansouto.compucar.model;

import lombok.Data;

@Data
public class Client {
    private Long number;
    private String name;
    private String email;
    private String phone;
    private String type;
}
