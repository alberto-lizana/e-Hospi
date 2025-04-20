package com.e.hospi.demo.Dto;

import java.time.LocalDateTime;

public class AppointmentResponseDto {

    private int idUser;
    private String nameDoctor; 
    private LocalDateTime fechaHora;

    // Constructor
    public AppointmentResponseDto(){}
    public AppointmentResponseDto(int idUser, String nameDoctor, LocalDateTime fechaHora) {
        this.idUser = idUser;
        this.nameDoctor = nameDoctor;
        this.fechaHora = fechaHora;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNameDoctor() {
        return nameDoctor;
    }

    public void setNameDoctor(String nameDoctor) {
        this.nameDoctor = nameDoctor;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
