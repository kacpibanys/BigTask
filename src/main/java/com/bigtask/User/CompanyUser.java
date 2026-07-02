package com.bigtask.User;

public class CompanyUser extends User{
    private final String companyName;
    private final String taxId;


    public CompanyUser(String email, String displayName, String companyName, String taxId) {
        super(email, displayName);
        if (companyName == null || companyName.isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be null or empty");
        }
        if (taxId == null || taxId.isEmpty()) {
            throw new IllegalArgumentException("Tax ID cannot be null or empty");
        }
        this.companyName = companyName;
        this.taxId = taxId;
    }
}
