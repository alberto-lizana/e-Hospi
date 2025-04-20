package com.e.hospi.demo.Dto;

public class UserResponseDto {
    
    // Atributes
    private String runUser;
    private String fullName;
    private String nameSex; 
    private String emailUser;
    private String phoneUser;
    private String nameRole;

    // Constructors
    public UserResponseDto() {}

    public UserResponseDto(String runUser, String fullName, 
                           String nameSex, String emailUser, 
                           String phoneUser, String nameRole) 
    {
        this.runUser = runUser;
        this.fullName = fullName;
        this.nameSex = nameSex;
        this.emailUser = emailUser;
        this.phoneUser = phoneUser;
        this.nameRole = nameRole;
    }

    public String getRunUser() {
        return runUser;
    }

    public void setRunUser(String runUser) {
        this.runUser = runUser;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNameSex() {
        return nameSex;
    }

    public void setNameSex(String nameSex) {
        this.nameSex = nameSex;
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

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }
}