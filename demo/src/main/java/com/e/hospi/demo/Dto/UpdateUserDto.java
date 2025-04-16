package com.e.hospi.demo.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



public class UpdateUserDto {

    // Atributes
    @Pattern(regexp = "^[0-9]{7,8}-[0-9kK]{1}$", message = "El RUN no es válido")
    private String runUser;

    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String firstNameUser;

    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String lastNameUser1;
    private String lastNameUser2;

    @Email(message = "El correo debe tener un formato válido")
    private String emailUser;

    @Size(min = 9, max = 9, message = "El teléfono debe tener exactamente 9 caracteres")
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe contener solo números")
    private String phoneUser;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String passwordUser;

    private int idRole;
    private int idSex; 

    // Constructors
    public UpdateUserDto() {}
    public UpdateUserDto(String runUser, String firstNameUser, 
                         String lastNameUser1, String lastNameUser2, 
                         String emailUser, String phoneUser, 
                         String passwordUser, int roleUser, 
                         int idSex) 
    {
        this.runUser = runUser;
        this.firstNameUser = firstNameUser;
        this.lastNameUser1 = lastNameUser1;
        this.lastNameUser2 = lastNameUser2;
        this.emailUser = emailUser;
        this.phoneUser = phoneUser;
        this.passwordUser = passwordUser;
        this.idRole = roleUser;
        this.idSex = idSex;  
    }

    // Getters and Setters
    public String getRunUser() {
        return runUser;
    }

    public void setRunUser(String runUser) {
        this.runUser = runUser;
    }

    public String getFirstNameUser() {
        return firstNameUser;
    }

    public void setFirstNameUser(String firstNameUser) {
        this.firstNameUser = firstNameUser;
    }

    public String getLastNameUser1() {
        return lastNameUser1;
    }

    public void setLastNameUser1(String lastNameUser1) {
        this.lastNameUser1 = lastNameUser1;
    }

    public String getLastNameUser2() {
        return lastNameUser2;
    }

    public void setLastNameUser2(String lastNameUser2) {
        this.lastNameUser2 = lastNameUser2;
    }

    public int getIdSex() {
        return idSex;
    }

    public void setIdSex(int idSex) {
        this.idSex = idSex;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }    
}