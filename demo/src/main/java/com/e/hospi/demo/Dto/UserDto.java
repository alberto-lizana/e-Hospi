package com.e.hospi.demo.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public class UserDto {

    // Atributes
    @NotBlank(message = "El RUN es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9kK]{1}$", message = "El RUN no es válido")
    private String runUser;


    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String firstNameUser;  

    @NotBlank(message = "El primer apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El primer apellido debe tener entre 2 y 50 caracteres")
    private String lastNameUser1;

    private String lastNameUser2;

    @Email(message = "El correo electrónico no es válido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    private String emailUser;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Size(min = 9, max = 9, message = "El teléfono debe tener exactamente 9 caracteres")
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe contener solo números")
    private String phoneUser;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String passwordUser;

    @NotNull(message = "El rol es obligatorio")
    private int idRole;

    @NotNull(message = "El sexo es obligatorio")
    private int idSex; 


    // Constructors
    public UserDto(String runUser, String firstNameUser, 
                   String lastNameUser1, String lastNameUser2, 
                   String emailUser, String phoneUser,
                   String passwordUser, int idRole,
                   int idSex) 
    {
        this.runUser = runUser;
        this.firstNameUser = firstNameUser;
        this.lastNameUser1 = lastNameUser1;

        this.emailUser = emailUser;
        this.phoneUser = phoneUser;
        this.passwordUser = passwordUser;
        this.idRole = idRole;
        this.idSex = idSex;
        
    }

    public UserDto(String runUser, String firstNameUser, 
                String lastNameUser1, 
                String emailUser, String phoneUser,
                String passwordUser, int idRole,
                int idSex) 
    {
        this.runUser = runUser;
        this.firstNameUser = firstNameUser;
        this.lastNameUser1 = lastNameUser1;
        this.emailUser = emailUser;
        this.phoneUser = phoneUser;
        this.passwordUser = passwordUser;
        this.idRole = idRole;
        this.idSex = idSex;

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
