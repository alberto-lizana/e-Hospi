package com.e.hospi.demo.Dto;

public class ResponseListOfPatientAppointmentToday {

    // Attributes
    private Long idAppointment;
    private String runPatient;
    private String fullNamePatient;
    private String fullNameDoctor;
    private int idDoctor;

    // Constructors
    public ResponseListOfPatientAppointmentToday() {}
    public ResponseListOfPatientAppointmentToday(Long idAppointment, String runPatient, 
                                                 String fullNamePatient, String fullNameDoctor, 
                                                 int idDoctor) 
    {
        this.idAppointment = idAppointment;
        this.runPatient = runPatient;
        this.fullNamePatient = fullNamePatient;
        this.fullNameDoctor = fullNameDoctor;
        this.idDoctor = idDoctor;
    }

    // Getters and Setters
    public Long getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(Long idAppointment) {
        this.idAppointment = idAppointment;
    }

    public String getRunPatient() {
        return runPatient;
    }

    public void setRunPatient(String runPatient) {
        this.runPatient = runPatient;
    }

    public String getFullNamePatient() {
        return fullNamePatient;
    }

    public void setFullNamePatient(String fullNamePatient) {
        this.fullNamePatient = fullNamePatient;
    }

    public String getFullNameDoctor() {
        return fullNameDoctor;
    }

    public void setFullNameDoctor(String fullNameDoctor) {
        this.fullNameDoctor = fullNameDoctor;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }
}
