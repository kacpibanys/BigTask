package com.bigtask.User;

public class User {
    private final String email;
    private String displayName;

    public User(String email,  String displayName) {
        if (email == null || displayName == null) {
            throw new IllegalArgumentException("Email and/or displayName cannot be null");
        }
        this.email = email;
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "User email: " + this.getEmail() +  " displayName: " + this.getDisplayName();
    }
}
