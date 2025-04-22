package com.e.hospi.demo.Dto;

public class ResponseListOfPatientAppointmentToday {

    // Attributes
    private Long idAppointment;
    private String runPatient;
    private String fullNamePatient;

    // Constructors
    public ResponseListOfPatientAppointmentToday() {}
    public ResponseListOfPatientAppointmentToday(Long idAppointment, String runPatient, 
                                                 String fullNamePatient) 
    {
        this.idAppointment = idAppointment;
        this.runPatient = runPatient;
        this.fullNamePatient = fullNamePatient;
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
}
