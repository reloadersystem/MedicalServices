package com.salud.medicalservices.entidades;

public class RegisterUser {

    private String firstName;
    private String lastName;
    private String email;
    private String identityDocument;
    private String address;
    private String phone;
    private String birthDate;
    private String genero;
    private String codigoPais;
    private String codigoDepartamento;
    private String codigoProvincia;
    private String codigoDistrito;
    private String password;
    private String userRole;


    public RegisterUser(String firstName, String lastName, String email, String identityDocument, String address, String phone, String birthDate, String genero, String codigoPais, String codigoDepartamento, String codigoProvincia, String codigoDistrito, String password, String userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.identityDocument = identityDocument;
        this.address = address;
        this.phone = phone;
        this.birthDate = birthDate;
        this.genero = genero;
        this.codigoPais = codigoPais;
        this.codigoDepartamento = codigoDepartamento;
        this.codigoProvincia = codigoProvincia;
        this.codigoDistrito = codigoDistrito;
        this.password = password;
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "RegisterUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", identityDocument='" + identityDocument + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", genero='" + genero + '\'' +
                ", codigoPais='" + codigoPais + '\'' +
                ", codigoDepartamento='" + codigoDepartamento + '\'' +
                ", codigoProvincia='" + codigoProvincia + '\'' +
                ", codigoDistrito='" + codigoDistrito + '\'' +
                ", password='" + password + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
