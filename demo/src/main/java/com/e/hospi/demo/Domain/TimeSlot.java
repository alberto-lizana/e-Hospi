package com.e.hospi.demo.Domain;

import java.time.LocalTime;

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
@Table(name = "time_slot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_time_slot")
    private Long idTimeSlot;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime; 

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime; 

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "idUser", nullable = false)
    private User doctor; 


    // Constructors
    public TimeSlot() {}
    public TimeSlot(LocalTime startTime, LocalTime endTime,
                    User doctor)
    {
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;

    }

     // Getters and Setters
    public Long getIdTimeSlot() {
        return idTimeSlot;
    }

    public void setIdTimeSlot(Long idTimeSlot) {
        this.idTimeSlot = idTimeSlot;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }
}