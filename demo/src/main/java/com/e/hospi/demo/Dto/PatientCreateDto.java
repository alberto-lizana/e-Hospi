package com.e.hospi.demo.Dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PatientCreateDto {

    @NotBlank(message = "El RUN es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9kK]{1}$", message = "El RUN no es válido")
    private String runPatient;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String firstnamePatient;

    @NotBlank(message = "El primer apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El primer apellido debe tener entre 2 y 50 caracteres")    
    private String lastnamePatient1;
 
    private String lastnamePatient2;

    @Email(message = "El correo electrónico no es válido")
    @NotBlank(message = "El correo electrónico es obligatorio")       
    private String emailPatient;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Size(min = 9, max = 9, message = "El teléfono debe tener exactamente 9 caracteres")
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe contener solo números")    
    private String phonePatient;

    @NotNull(message = "La fecha de nacimiento es obligatoria.")
    @Past(message = "La fecha de nacimiento debe estar en el pasado.")
    private LocalDate bornDatePatient;

    @NotNull (message = "La previsión médica es obligatoria.")
    private int idHealthInsurancePatient;

    @NotNull (message = "El sexo es obligatorio.")
    private int idSexPatient;

    // Constructors
    public PatientCreateDto(){}
    public PatientCreateDto(String runPatient, String firstnamePatient, 
                            String lastnamePatient1, String lastnamePatient2, 
                            String emailPatient, String phonePatient, 
                            LocalDate bornDatePatient, int idHealthInsurancePatient, 
                            int idSexPatient) {
        this.runPatient = runPatient;
        this.firstnamePatient = firstnamePatient;
        this.lastnamePatient1 = lastnamePatient1;
        this.lastnamePatient2 = lastnamePatient2;
        this.emailPatient = emailPatient;
        this.phonePatient = phonePatient;
        this.bornDatePatient = bornDatePatient;
        this.idHealthInsurancePatient = idHealthInsurancePatient;
        this.idSexPatient = idSexPatient;
    }

    // Getters and Setters
    public String getRunPatient() {
        return runPatient;
    }

    public void setRunPatient(String runPatient) {
        this.runPatient = runPatient;
    }

    public String getFirstnamePatient() {
        return firstnamePatient;
    }

    public void setFirstnamePatient(String firstnamePatient) {
        this.firstnamePatient = firstnamePatient;
    }

    public String getLastnamePatient1() {
        return lastnamePatient1;
    }

    public void setLastnamePatient1(String lastnamePatient1) {
        this.lastnamePatient1 = lastnamePatient1;
    }

    public String getLastnamePatient2() {
        return lastnamePatient2;
    }

    public void setLastnamePatient2(String lastnamePatient2) {
        this.lastnamePatient2 = lastnamePatient2;
    }

    public String getEmailPatient() {
        return emailPatient;
    }

    public void setEmailPatient(String emailPatient) {
        this.emailPatient = emailPatient;
    }

    public String getPhonePatient() {
        return phonePatient;
    }

    public void setPhonePatient(String phonePatient) {
        this.phonePatient = phonePatient;
    }

    public LocalDate getBornDatePatient() {
        return bornDatePatient;
    }

    public void setBornDatePatient(LocalDate bornDatePatient) {
        this.bornDatePatient = bornDatePatient;
    }

    public int getIdHealthInsurancePatient() {
        return idHealthInsurancePatient;
    }

    public void setIdHealthInsurancePatient(int idHealthInsurancePatient) {
        this.idHealthInsurancePatient = idHealthInsurancePatient;
    }

    public int getIdSexPatient() {
        return idSexPatient;
    }

    public void setIdSexPatient(int idSexPatient) {
        this.idSexPatient = idSexPatient;
    }
}





