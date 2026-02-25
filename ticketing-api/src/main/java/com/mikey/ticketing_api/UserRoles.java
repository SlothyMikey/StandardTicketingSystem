package com.mikey.ticketing_api;

public enum UserRoles {
    ADMIN("admin"),
    AGENT("agent"),
    CUSTOMER("customer");

    private final String role;

    UserRoles(String role) {
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
