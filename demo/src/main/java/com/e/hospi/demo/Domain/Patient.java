package com.e.hospi.demo.Domain;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient")
public class Patient {

    // Atributes
    @Id
    @Column(name = "run_patient", nullable = false, unique = true)
    private String runPatient;

    @Column(name = "first_name_patient", nullable = false)
    private String firstNamePatient;

    @Column(name = "last_name_patient1", nullable = false)
    private String lastnamePatient1;

    @Column(name = "last_name_patient2")
    private String lastnamePatient2;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sex", referencedColumnName = "id_sex", nullable = false )
    private Sex sexPatient;

    @Column(name = "born_date_patient", nullable = false)
    private LocalDate bornDatePatient;

    @Column(name = "phone_patient", nullable = false, unique = true)
    private String phonePatient;

    @Column(name = "email_patient", nullable = false, unique = true)
    private String emailPatient;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_health_insurance", referencedColumnName = "id_health_insurance", nullable = false )
    private HealthInsurance healthinsurancePatient;


    // Constructors
    public Patient() {}
    public Patient(String runPatient, String firstNamePatient, 
                   String lastnamePatient1, String lastnamePatient2, 
                   Sex sexPatient, LocalDate bornDatePatient, 
                   String phonePatient, String emailPatient, 
                   List <Appointment> appointments, HealthInsurance healthinsurancePatient) 
    {
        this.runPatient = runPatient;
        this.firstNamePatient = firstNamePatient;
        this.lastnamePatient1 = lastnamePatient1;
        this.lastnamePatient2 = lastnamePatient2;
        this.sexPatient = sexPatient;
        this.bornDatePatient = bornDatePatient;
        this.phonePatient = phonePatient;
        this.emailPatient = emailPatient;
        this.appointments = appointments;
        this.healthinsurancePatient = healthinsurancePatient;
    }

    // Getters And Setters
    public String getRunPatient() {
        return runPatient;
    }

    public void setRunPatient(String runPatient) {
        this.runPatient = runPatient;
    }

    public String getFirstNamePatient() {
        return firstNamePatient;
    }

    public void setFirstNamePatient(String firstNamePatient) {
        this.firstNamePatient = firstNamePatient;
    }

    public String getLastnamePatient1() {
        return lastnamePatient1;
    }

    public void setLastnamePatient1(String lastnamePatient1) {
        this.lastnamePatient1 = lastnamePatient1;
    }

    public String getLastnamePatient2() {
        return lastnamePatient2;
    }

    public void setLastnamePatient2(String lastnamePatient2) {
        this.lastnamePatient2 = lastnamePatient2;
    }

    public Sex getSexPatient() {
        return sexPatient;
    }

    public void setSexPatient(Sex sexPatient) {
        this.sexPatient = sexPatient;
    }

    public LocalDate getBornDatePatient() {
        return bornDatePatient;
    }

    public void setBornDatePatient(LocalDate bornDatePatient) {
        this.bornDatePatient = bornDatePatient;
    }

    public String getPhonePatient() {
        return phonePatient;
    }

    public void setPhonePatient(String phonePatient) {
        this.phonePatient = phonePatient;
    }

    public String getEmailPatient() {
        return emailPatient;
    }

    public void setEmailPatient(String emailPatient) {
        this.emailPatient = emailPatient;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public HealthInsurance getHealthinsurancePatient() {
        return healthinsurancePatient;
    }

    public void setHealthinsurancePatient(HealthInsurance healthinsurancePatient) {
        this.healthinsurancePatient = healthinsurancePatient;
    }    
}
