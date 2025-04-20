package com.e.hospi.demo.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ResponseAllAppointmensPatientDto {

    // Attributes
    private Long idAppointment;
    private LocalDate dateAppointment;
    private LocalTime dateTimeAppointment;
    private String doctorFullName;
    private String patientFullName;

    // Constructor
    public ResponseAllAppointmensPatientDto(Long idAppointment, LocalDate dateAppointment,
                                            LocalTime dateTimeAppointment, String doctorFullName, 
                                            String patientFullName) 
    {
        this.idAppointment = idAppointment;
        this.dateAppointment = dateAppointment;
        this.dateTimeAppointment = dateTimeAppointment;
        this.doctorFullName = doctorFullName;
        this.patientFullName = patientFullName;
    }

    // Getters and Setters
    public Long getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(Long idAppointment) {
        this.idAppointment = idAppointment;
    }

    public LocalDate getDateAppointment() {
        return dateAppointment;
    }

    public void setDateAppointment(LocalDate dateAppointment) {
        this.dateAppointment = dateAppointment;
    }

    public LocalTime getDateTimeAppointment() {
        return dateTimeAppointment;
    }

    public void setDateTimeAppointment(LocalTime dateTimeAppointment) {
        this.dateTimeAppointment = dateTimeAppointment;
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public void setDoctorFullName(String doctorFullName) {
        this.doctorFullName = doctorFullName;
    }

    public String getPatientFullName() {
        return patientFullName;
    }

    public void setPatientFullName(String patientFullName) {
        this.patientFullName = patientFullName;
    }   
}
