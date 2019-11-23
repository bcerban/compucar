package com.cerbansouto.compucar.jobs;

import lombok.Data;

@Data
public class Heartbeat {
    private String name;
    private int port;

    public Heartbeat(String name, int port) {
        this.name = name;
        this.port = port;
    }
}
