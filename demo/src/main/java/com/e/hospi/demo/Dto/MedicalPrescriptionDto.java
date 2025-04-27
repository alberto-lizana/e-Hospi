package com.e.hospi.demo.Dto;

import jakarta.validation.constraints.Size;

public class MedicalPrescriptionDto {

    // Attributes
    private Long idAppointment;

    @Size(max = 1000, message = "La prescripción médica no puede tener más de 1000 caracteres.")
    private String prescribedMedicationsAppointment;

    // Constructors
    public MedicalPrescriptionDto() {}
    public MedicalPrescriptionDto(Long idAppointment, String prescribedMedicationsAppointment) {
        this.idAppointment = idAppointment;
        this.prescribedMedicationsAppointment = prescribedMedicationsAppointment;
    }

     // Getters and Setters
    public Long getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(Long idAppointment) {
        this.idAppointment = idAppointment;
    }

    public String getPrescribedMedicationsAppointment() {
        return prescribedMedicationsAppointment;
    }
    
    public void setPrescribedMedicationsAppointment(String prescribedMedicationsAppointment) {
        this.prescribedMedicationsAppointment = prescribedMedicationsAppointment;
    }    
}
