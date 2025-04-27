package com.e.hospi.demo.Dto;

import java.time.LocalDateTime;

public class MedicalHistoryDto {

    // Attributes
    private LocalDateTime dateAppointment;
    private String diagnosis;
    private String treatment;
    private String medications;

    // Constructors
    public MedicalHistoryDto() {}
    public MedicalHistoryDto(LocalDateTime dateAppointment, String diagnosis, 
                             String treatment, String medications)
    {
        this.dateAppointment = dateAppointment;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.medications = medications;
    }


    // Getters and Setters
    public LocalDateTime getDateAppointment() {
        return dateAppointment;
    }

    public void setDateAppointment(LocalDateTime dateAppointment) {
        this.dateAppointment = dateAppointment;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }
}

