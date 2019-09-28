package com.cerbansouto.compucar.model;

import lombok.Data;

@Data
public class Reader {

    private String code;
    private String brand;
    private int batteryLife;
    private int batteryUsed;

    private Workshop workshop;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(int batteryLife) {
        this.batteryLife = batteryLife;
    }

    public int getBatteryUsed() {
        return batteryUsed;
    }

    public void setBatteryUsed(int batteryUsed) {
        this.batteryUsed = batteryUsed;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    public int getRemainingBatteryLifetime()
    {
        return batteryLife - batteryUsed;
    }
}
