package com.example.final_project.entity.enums;

public enum Role {
    CLIENT("USER"),
    ADMINISTRATOR("Administrator");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
