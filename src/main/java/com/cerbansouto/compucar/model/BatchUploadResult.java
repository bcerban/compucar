package com.cerbansouto.compucar.model;

import lombok.Data;

import java.util.List;

@Data
public class BatchUploadResult {
    private List<String> messages;
    private boolean success;
}
