package com.example.final_project.entity.enums;

public enum Status {
    CREATED("Created"),
    CANCEL("Cancel"),
    WAIT_PAYMENT("Wait payment"),
    PAID("Paid"),
    ON_THE_WAY("On the way"),
    DELIVERED("Delivered");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
