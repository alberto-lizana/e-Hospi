package com.e.hospi.demo.Dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Pattern;

public class AppointmentFilterDto {

    @Pattern(regexp = "^[0-9]{7,8}-[0-9kK]{1}$", message = "El RUN no es válido")
    private String runPatient;


    private String idUser;

    @Future (message = "La fecha de agendamiento debe estar en el futuro.")
    private LocalDate initialDate;

    @Future (message = "La fecha de agendamiento debe estar en el futuro.")
    private LocalDate finalDate;

    // constructors
    public AppointmentFilterDto() {}
    public AppointmentFilterDto(String runPatient, String idUser, 
                                LocalDate initialDate, LocalDate finalDate) 
    {
        this.runPatient = runPatient;
        this.idUser = idUser;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }

    // getters and setters
    public String getRunPatient() {
        return runPatient;
    }

    public void setRunPatient(String runPatient) {
        this.runPatient = runPatient;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }
}
