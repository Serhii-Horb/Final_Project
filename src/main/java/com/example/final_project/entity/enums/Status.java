package com.example.final_project.entity.enums;

public enum Status {
    CREATED("Created"),
    CANCELED("Canceled"),
    AWAITING_PAYMENT("Awaiting payment"),
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
