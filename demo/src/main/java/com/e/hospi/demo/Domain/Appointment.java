package com.e.hospi.demo.Domain;

import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "run_patient", referencedColumnName = "run_patient", nullable = false)
    private Patient patient;
    
    // Constructors
    public Appointment() {}
    public Appointment(Long idAppointment, LocalDateTime dateAppointment, Patient patient) 
    {
        this.idAppointment = idAppointment;
        this.dateAppointment = dateAppointment;
        this.patient = patient;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;

    }
}

