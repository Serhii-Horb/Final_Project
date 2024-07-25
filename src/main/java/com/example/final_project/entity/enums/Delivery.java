package com.example.final_project.entity.enums;

import com.example.final_project.entity.deserializer.DeliveryDeserializer;
import com.example.final_project.exceptions.BadRequestException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = DeliveryDeserializer.class)
public enum Delivery {
    /**
     * Courier delivery method.
     */
    COURIER_DELIVERY("Courier Delivery"),

    /**
     * Self-pickup delivery method.
     */
    SELF_DELIVERY("Self Delivery"),

    /**
     * Delivery to a department.
     */
    DEPARTMENT_DELIVERY("Department Delivery");

    /**
     * Name of the delivery method.
     */
    private String delivery_name;

    /**
     * Constructor to initialize the delivery method name.
     *
     * @param delivery_name Name of the delivery method.
     */
    Delivery(String delivery_name) {
        this.delivery_name = delivery_name;
    }

    /**
     * Gets the name of the delivery method.
     *
     * @return Name of the delivery method.
     */
    public String getValue() {
        return delivery_name;
    }

    /**
     * Converts a string representation of a delivery method to the corresponding enum value.
     *
     * @param delivery_name Name of the delivery method as a string.
     * @return Corresponding {@link Delivery} enum value.
     * @throws BadRequestException if the delivery method is invalid.
     */
    public static Delivery fromText(String delivery_name) {
        for (Delivery deliveryMethod : Delivery.values()) {
            if (deliveryMethod.getValue().equals(delivery_name)) {
                return deliveryMethod;
            }
        }
        throw new BadRequestException("Invalid delivery method");
    }
}
