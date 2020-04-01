package com.salud.medicalservices.entidades;

public class AuthUser {

    private String email;
    private String password;
    private String userRole;

    public AuthUser(String email, String password, String userRole) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
