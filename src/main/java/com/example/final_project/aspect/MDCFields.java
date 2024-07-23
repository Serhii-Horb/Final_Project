package com.example.final_project.aspect;

import org.slf4j.MDC;

public enum MDCFields {
    // Enumeration values for different MDC fields
    REQUEST_ID("REQUEST_ID"),
    DAO_STEP("DAO_STEP"),
    DAO_METHOD("DAO_METHOD"),
    DAO_TIME("DAO_TIME"),
    SERVICE_STEP("SERVICE_STEP"),
    SERVICE_METHOD("SERVICE_METHOD"),
    SERVICE_TIME("SERVICE_TIME"),
    CONTROLLER_STEP("CONTROLLER_STEP"),
    CONTROLLER_METHOD("CONTROLLER_METHOD"),
    CONTROLLER_TIME("CONTROLLER_TIME");

    private final String mdcFieldName;

    /**
     * Constructor to initialize the MDC field name.
     *
     * @param mdcFieldName the name of the MDC field
     */
    MDCFields(final String mdcFieldName) {
        this.mdcFieldName = mdcFieldName;
    }

    /**
     * Adds a value to the MDC field.
     * This method puts the specified value into the MDC under the field name associated with this enum constant.
     *
     * @param mdcFieldValue the value to be added to the MDC field
     */
    public void putMdcField(final Object mdcFieldValue) {
        MDC.put(mdcFieldName, String.valueOf(mdcFieldValue));
    }

    /**
     * Adds a value to the MDC field with additional context information.
     * This method puts the specified value into the MDC under the field name associated with this enum constant,
     * including the field name as part of the value for better context.
     *
     * @param mdcFieldValue the value to be added to the MDC field
     */
    public void putMdcFieldWithFieldName(final Object mdcFieldValue) {
        putMdcField("{" + mdcFieldName + "=" + mdcFieldValue + "}");
    }

    /**
     * Removes the MDC field.
     * This method removes the value associated with the field name of this enum constant from the MDC.
     */
    public void removeMdcField() {
        MDC.remove(mdcFieldName);
    }
}