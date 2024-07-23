package com.example.final_project.entity.enums;

public enum Status {
    /**
     * Order has been created but not yet processed.
     */
    CREATED("Created"),

    /**
     * Order has been canceled and will not be processed.
     */
    CANCELED("Canceled"),

    /**
     * Order is awaiting payment from the customer.
     */
    AWAITING_PAYMENT("Awaiting payment"),

    /**
     * Payment for the order has been received.
     */
    PAID("Paid"),

    /**
     * Order is currently in transit to the customer.
     */
    ON_THE_WAY("On the way"),

    /**
     * Order has been delivered to the customer.
     */
    DELIVERED("Delivered");

    /**
     * The textual representation of the status.
     */
    private String status;

    /**
     * Constructor to initialize the status text.
     *
     * @param status The textual representation of the status.
     */
    Status(String status) {
        this.status = status;
    }

    /**
     * Gets the textual representation of the status.
     *
     * @return The textual representation of the status.
     */
    public String getStatus() {
        return status;
    }
}
