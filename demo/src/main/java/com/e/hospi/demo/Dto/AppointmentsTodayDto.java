package com.e.hospi.demo.Dto;

import java.time.LocalDateTime;

public class AppointmentsTodayDto {
    
    private String runPatient;
    private String fullNamePatient;
    private String fullNameDoctor;
    private LocalDateTime dateAppointment;


    // Constructors
    public AppointmentsTodayDto(){}
    public AppointmentsTodayDto(String runPatient, String fullNamePatient, 
                                String fullNameDoctor, LocalDateTime dateAppointment) 
    {
        this.runPatient = runPatient;
        this.fullNamePatient = fullNamePatient;
        this.fullNameDoctor = fullNameDoctor;
        this.dateAppointment = dateAppointment;
    }

    public String getRunPatient() {
        return runPatient;
    }

    public void setRunPatient(String runPatient) {
        this.runPatient = runPatient;
    }

    public String getFullNamePatient() {
        return fullNamePatient;
    }

    public void setFullNamePatient(String fullNamePatient) {
        this.fullNamePatient = fullNamePatient;
    }

    public String getFullNameDoctor() {
        return fullNameDoctor;
    }

    public void setFullNameDoctor(String fullNameDoctor) {
        this.fullNameDoctor = fullNameDoctor;
    }

    public LocalDateTime getDateAppointment() {
        return dateAppointment;
    }

    public void setDateAppointment(LocalDateTime dateAppointment) {
        this.dateAppointment = dateAppointment;
    }
}


