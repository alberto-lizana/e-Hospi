package com.e.hospi.demo.Dto;

import java.time.LocalDateTime;

public class PostAppointmentDto {

    private LocalDateTime dateAppointment;
    private String  idDoctor;
    private String runPatient;

    // Constructor
    public PostAppointmentDto() {}
    public PostAppointmentDto(LocalDateTime dateAppointment, String  idDoctor, String runPatient) {
        this.dateAppointment = dateAppointment;
        this.idDoctor = idDoctor;
        this.runPatient = runPatient;
    }


    // Getters and Setters
    public LocalDateTime getDateAppointment() {
        return dateAppointment;
    }

    public void setDateAppointment(LocalDateTime dateAppointment) {
        this.dateAppointment = dateAppointment;
    }

    public String  getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(String  idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getRunPatient() {
        return runPatient;
    }

    public void setRunPatient(String runPatient) {
        this.runPatient = runPatient;
    }
}
