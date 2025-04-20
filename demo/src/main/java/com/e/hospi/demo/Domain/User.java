package com.e.hospi.demo.Domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "user")
public class User {

    // Atributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;

    @Column(nullable = false, unique = true, name = "run_user")
    private String runUser;
    
    @Column(nullable = false, name = "first_name_user")
    private String firstNameUser;
    
    @Column(nullable = false, name = "last_name_user1")
    private String lastNameUser1;
    
    @Column(nullable = false, name = "last_name_user2")
    private String lastNameUser2;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sex", referencedColumnName = "id_sex", nullable = false )
    private Sex sexUser; 
    
    @Column(nullable = false, unique = true, name = "email_user")
    private String emailUser;
    
    @Column(nullable = false, unique = true, name = "phone_user")
    private String phoneUser;
    
    @Column(nullable = false, name = "password_user")
    private String passwordUser;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", nullable = false )
    private Role roleUser;

    @JsonManagedReference("doctor-appointments")
    @OneToMany(mappedBy = "assignedDoctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointmentsAsDoctor;


    // Constructors
    public User() {}
    public User(String runUser, String firstNameUser,
                String lastNameUser1, String lastNameUser2, 
                Sex sexUser, String emailUser, 
                String phoneUser, String passwordUser, 
                Role roleUser, List<Appointment> appointmentsAsDoctor)
    {
        this.runUser = runUser;
        this.firstNameUser = firstNameUser;
        this.lastNameUser1 = lastNameUser1;
        this.lastNameUser2 = lastNameUser2;
        this.sexUser = sexUser;
        this.emailUser = emailUser;
        this.phoneUser = phoneUser;
        this.passwordUser = passwordUser;
        this.roleUser = roleUser;
        this.appointmentsAsDoctor = appointmentsAsDoctor;
    }

    // Getters And Setters
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getRunUser() {
        return runUser;
    }

    public void setRunUser(String runUser) {
        this.runUser = runUser;
    }

    public String getFirstNameUser() {
        return firstNameUser;
    }

    public void setFirstNameUser(String firstNameUser) {
        this.firstNameUser = firstNameUser;
    }

    public String getLastNameUser1() {
        return lastNameUser1;
    }

    public void setLastNameUser1(String lastNameUser1) {
        this.lastNameUser1 = lastNameUser1;
    }

    public String getLastNameUser2() {
        return lastNameUser2;
    }

    public void setLastNameUser2(String lastNameUser2) {
        this.lastNameUser2 = lastNameUser2;
    }

    public Sex getSexUser() {
        return sexUser;
    }

    public void setSexUser(Sex sexUser) {
        this.sexUser = sexUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public Role getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(Role roleUser) {
        this.roleUser = roleUser;
    }

    public List<Appointment> getAppointmentsAsDoctor() {
        return appointmentsAsDoctor;
    }

    public void setAppointmentsAsDoctor(List<Appointment> appointmentsAsDoctor) {
        this.appointmentsAsDoctor = appointmentsAsDoctor;
    }
}
