package com.e.hospi.demo.Dto;

public class ResponseGetPatientByDoctor {

    private String runPatient;
    private String fullNamePatient;

    public ResponseGetPatientByDoctor() {} 
    
    public ResponseGetPatientByDoctor(String runPatient, String fullNamePatient) {
        this.runPatient = runPatient;
        this.fullNamePatient = fullNamePatient;
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
