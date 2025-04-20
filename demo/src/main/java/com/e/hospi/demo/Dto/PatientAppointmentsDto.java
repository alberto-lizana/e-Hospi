package com.e.hospi.demo.Dto;

import jakarta.validation.constraints.Pattern;

public class PatientAppointmentsDto {

    @Pattern(regexp = "^[0-9]{7,8}-[0-9kK]{1}$", message = "El RUN no es válido")
    private String runPatient;

    public PatientAppointmentsDto() {} 
    public PatientAppointmentsDto(String runPatient) {
        this.runPatient = runPatient;
    }

    public String getRunPatient() {
        return runPatient;
    }

    public void setRunPatient(String runPatient) {
        this.runPatient = runPatient;
    }
}
