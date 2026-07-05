package com.bigtask.User;

public class IndividualUser extends User {

    public IndividualUser(String email, String displayName, String studentId) {
        super(email, displayName);
        if (studentId == null || studentId.isEmpty()) {
            throw new IllegalArgumentException("Student id cannot be null or empty");
        }
    }
}
