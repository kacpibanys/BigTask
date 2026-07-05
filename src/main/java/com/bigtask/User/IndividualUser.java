package com.bigtask.User;

public class IndividualUser extends User {

    public IndividualUser(String email, String displayName) {
        super(email, displayName);
        /*if (studentId == null || studentId.isEmpty()) {
            throw new IllegalArgumentException("Student id cannot be null or empty");
        }*/
    }

    @Override
    public String toString() {
        return "[INDIVIDUAL] " + super.toString();
    }
}

/*
LIST_USERS



ADD_USER INDIVIDUAL adam.nowak@gmail.com Adam Nowak


ADD_USER COMPANY biuro@polbud.pl Adam_Nowak Polbud Enterprise sp. z o.o. 9876543210


LIST_USERS


ADD_USER COMPANY bad@test.pl

*/
