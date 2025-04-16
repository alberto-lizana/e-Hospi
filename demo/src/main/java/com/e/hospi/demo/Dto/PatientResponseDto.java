package com.e.hospi.demo.Dto;

public class PatientResponseDto {

    // Atributes
    private String runPatient;
    private String FullnamePatient;
    private String emailPatient;
    private String phonePatient;
    private String nameHealthInsurance;
    private String nameSex;

    // Constructors
    public PatientResponseDto() {}

    public PatientResponseDto(String runPatient, String FullnamePatient,
                              String emailPatient, String phonePatient, 
                              String nameHealthInsurance, String nameSex) 
    {
        this.runPatient = runPatient;
        this.FullnamePatient = FullnamePatient;
        this.emailPatient = emailPatient;
        this.phonePatient = phonePatient;
        this.nameHealthInsurance = nameHealthInsurance;
        this.nameSex = nameSex;
    }

    // Getters and Setters
    public String getRunPatient() {
        return runPatient;
    }

    public void setRunPatient(String runPatient) {
        this.runPatient = runPatient;
    }

    public String getFullnamePatient() {
        return FullnamePatient;
    }

    public void setFullnamePatient(String FullnamePatient) {
        this.FullnamePatient = FullnamePatient;
    }

    public String getEmailPatient() {
        return emailPatient;
    }

    public void setEmailPatient(String emailPatient) {
        this.emailPatient = emailPatient;
    }

    public String getPhonePatient() {
        return phonePatient;
    }

    public void setPhonePatient(String phonePatient) {
        this.phonePatient = phonePatient;
    }

    public String getNameHealthInsurance() {
        return nameHealthInsurance;
    }

    public void setNameHealthInsurance(String nameHealthInsurance) {
        this.nameHealthInsurance = nameHealthInsurance;
    }

    public String getNameSex() {
        return nameSex;
    }

    public void setNameSex(String nameSex) {
        this.nameSex = nameSex;
    }

    
}
