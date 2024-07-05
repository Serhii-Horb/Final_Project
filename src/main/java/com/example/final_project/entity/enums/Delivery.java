package com.example.final_project.entity.enums;

public enum Delivery {
    COURIER_DELIVERY("Courier Delivery"),
    SELF_DELIVERY("Self Delivery"),
    DEPARTMENT_DELIVERY("Department Delivery");

    private String delivery_name;

    Delivery(String delivery_name) {
        this.delivery_name = delivery_name;
    }

    public String getValue() {
        return delivery_name;
    }
}
