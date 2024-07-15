package com.example.final_project.entity.enums;

import com.example.final_project.entity.desirializer.DeliveryDeserializer;
import com.example.final_project.exceptions.BadRequestException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = DeliveryDeserializer.class)
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

//    @JsonCreator
    public static Delivery fromText(String delivery_name) {
        for (Delivery deliveryMethod : Delivery.values()) {
            if(deliveryMethod.getValue().equals(delivery_name)) {
                return deliveryMethod;
            }
        }
        throw new BadRequestException("Invalid delivery method");
    }

//    @Override
//    public String toString() {
//        return delivery_name;
//    }
}
