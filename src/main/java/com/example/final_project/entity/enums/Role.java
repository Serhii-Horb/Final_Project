package com.example.final_project.entity.enums;

public enum Role {
    USER("User"),
    ADMINISTRATOR("Administrator");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
