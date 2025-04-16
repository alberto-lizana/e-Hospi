package com.e.hospi.demo.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdatePatientDto {

    // Atributes
    @Pattern(regexp = "^[0-9]{7,8}-[0-9kK]{1}$", message = "El RUN no es válido")
    private String runPatient;

    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String firstnamePatient;

    @Size(min = 2, max = 50, message = "El primer apellido debe tener entre 2 y 50 caracteres")    
    private String lastnamePatient1;
 
    private String lastnamePatient2;

    @Email(message = "El correo electrónico no es válido")    
    private String emailPatient;

    @Size(min = 9, max = 9, message = "El teléfono debe tener exactamente 9 caracteres")
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe contener solo números")    
    private String phonePatient;

    private int idHealthInsurancePatient;

    private int idSexPatient;

    // Constructors
    public UpdatePatientDto(){}
    public UpdatePatientDto(String runPatient, String firstnamePatient,
                            String lastnamePatient1, String lastnamePatient2,
                            String emailPatient, String phonePatient,
                            int idHealthInsurancePatient, int idSexPatient) 
    {
        this.runPatient = runPatient;
        this.firstnamePatient = firstnamePatient;
        this.lastnamePatient1 = lastnamePatient1;
        this.lastnamePatient2 = lastnamePatient2;
        this.emailPatient = emailPatient;
        this.phonePatient = phonePatient;
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
