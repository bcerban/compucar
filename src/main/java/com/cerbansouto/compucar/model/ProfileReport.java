package com.cerbansouto.compucar.model;

import lombok.Data;

import java.util.List;

@Data
public class ProfileReport {
    private List<Profile> executionTimes;
    private Profile fastestService;
    private Profile slowestService;
    private Profile mostUsed;
    private Profile leastUsed;
}
