package com.e.hospi.demo.Dto;

import jakarta.validation.constraints.Size;

public class DiagnosisAndTreatmentDto {

    // Atributes
    private Long idAppointment;

    @Size(max = 1000, message = "El diagnóstico no puede tener más de 1000 caracteres.")
    private String diagnosis;
    
    @Size(max = 1000, message = "El diagnóstico no puede tener más de 1000 caracteres.")
    private String treatment;

    // Constructor
    public DiagnosisAndTreatmentDto() {}
    public DiagnosisAndTreatmentDto(Long idAppointment, String diagnosis, String treatment) {
        this.idAppointment = idAppointment;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    // Getters and Setters
    public Long getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(Long idAppointment) {
        this.idAppointment = idAppointment;
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
}
