package com.e.hospi.demo.Dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class UserDto {

    // Atributes
    @NotBlank(message = "El RUN no puede ser nulo")
    @Size(min = 9, max = 10, message = "El RUN debe tener 9 o 10 caracteres")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "El RUN debe ser válido (ej: 12345678-9)")
    private String runUser; 

    @NotBlank(message = "El primer nombre no puede estar vacío")
    private String firstNameUser;  

    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastNameUser1;

    private String lastNameUser2;

    @NotNull(message = "El sexo es obligatorio")
    private int idSex; 

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email no es válido")
    private String emailUser;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Size(min = 9, max = 9, message = "El teléfono debe tener exactamente 9 caracteres")
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe contener solo números")
    private String phoneUser;
    
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String passwordUser;

    @NotNull(message = "El rol es obligatorio")
    private int idRole;

    // Constructors
    public UserDto(String runUser, String firstNameUser, 
                   String lastNameUser1, String lastNameUser2, 
                   int idSex, String emailUser,
                   String phoneUser, String passwordUser, String sex) 
    {
        this.runUser = runUser;
        this.firstNameUser = firstNameUser;
        this.lastNameUser1 = lastNameUser1;
        this.lastNameUser2 = lastNameUser2;
        this.idSex = idSex;
        this.emailUser = emailUser;
    }

    public UserDto() {}


    // Getters And Setters
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
