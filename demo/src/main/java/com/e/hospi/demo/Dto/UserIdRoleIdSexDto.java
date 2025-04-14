package com.e.hospi.demo.Dto;

public class UserIdRoleIdSexDto {

    private int idSex;
    private int idRole;
    private String emailUser;


    // Constructors
    public UserIdRoleIdSexDto() {}
    public UserIdRoleIdSexDto(int idSex, int idRole, String emailUser) {
        this.idSex = idSex;
        this.idRole = idRole;
        this.emailUser = emailUser;

    }

    // Getters and Setters
    public int getIdSex() {
        return idSex;
    }

    public void setIdSex(int idSex) {
        this.idSex = idSex;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }      
}
