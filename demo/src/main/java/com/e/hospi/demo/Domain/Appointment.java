package com.e.hospi.demo.Domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment")
public class Appointment {

    // Atributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_appointment")
    private Long idAppointment;

    @Column(name = "date_appointment", nullable = false)
    private LocalDateTime dateAppointment;

    @Column(name= "diagnosis_appointment", nullable = true, length = 1000)
    private String diagnosisAppointment;

    @Column(name= "treatment_appointment", nullable = true, length = 1000)
    private String treatmentAppointment;

    @Column(name= "prescribed_medication_appointment", nullable = true)
    private String prescribedMedicationsAppointment;

    @JsonBackReference("doctor-appointments")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) 
    @JoinColumn(name = "id_user", referencedColumnName = "idUser", nullable = false)
    private User assignedDoctor;

    @JsonBackReference("patient-appointments")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_patient", referencedColumnName = "id_patient", nullable = false)
    private Patient patient;

    @Column(name= "status_appointment", nullable = true)
    private boolean statusAppointment = false; // false = aNo pagada, true = pagada 

    @Column(name= "seen_appointment", nullable = true)
    private boolean isSeen = false; // false = no atendida, true = atendida

    
    // Constructors
    public Appointment() {}
    public Appointment(LocalDateTime dateAppointment, String diagnosisAppointment,
                       String treatmentAppointment, String prescribedMedicationsAppointment, 
                       User assignedDoctor, Patient patient,
                       boolean statusAppointment, boolean isSeen) 
    {
        this.dateAppointment = dateAppointment;
        this.diagnosisAppointment = diagnosisAppointment;
        this.treatmentAppointment = treatmentAppointment;
        this.prescribedMedicationsAppointment = prescribedMedicationsAppointment;
        this.assignedDoctor = assignedDoctor;
        this.patient = patient;
        this.statusAppointment = statusAppointment;
        this.isSeen = isSeen;
    }
    
    // Getters And Setters
    public Long getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(Long idAppointment) {
        this.idAppointment = idAppointment;
    }

    public LocalDateTime getDateAppointment() {
        return dateAppointment;
    }

    public void setDateAppointment(LocalDateTime dateAppointment) {
        this.dateAppointment = dateAppointment;
    } 

    public String getDiagnosisAppointment() {
        return diagnosisAppointment;
    }

    public void setDiagnosisAppointment(String diagnosisAppointment) {
        this.diagnosisAppointment = diagnosisAppointment;
    }

    public String getTreatmentAppointment() {
        return treatmentAppointment;
    }

    public void setTreatmentAppointment(String treatmentAppointment) {
        this.treatmentAppointment = treatmentAppointment;
    }

    public String getPrescribedMedicationsAppointment() {
        return prescribedMedicationsAppointment;
    }

    public void setPrescribedMedicationsAppointment(String prescribedMedicationsAppointment) {
        this.prescribedMedicationsAppointment = prescribedMedicationsAppointment;
    }

    public User getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(User assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public boolean isStatusAppointment() {
        return statusAppointment;
    }

    public void setStatusAppointment(boolean statusAppointment) {
        this.statusAppointment = statusAppointment;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean isSeen) {
        this.isSeen = isSeen;
    }
}

