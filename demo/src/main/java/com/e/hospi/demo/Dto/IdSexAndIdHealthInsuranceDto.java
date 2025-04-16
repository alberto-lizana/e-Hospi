package com.e.hospi.demo.Dto;


public class IdSexAndIdHealthInsuranceDto {

    private int idSexPatient;
    private int idHealthInsurancePatient;

    // Constructors
    public IdSexAndIdHealthInsuranceDto() {}
    public IdSexAndIdHealthInsuranceDto(int idSexPatient, int idHealthInsurancePatient)  {
        this.idSexPatient = idSexPatient;
        this.idHealthInsurancePatient = idHealthInsurancePatient;
    }

    // Getters and Setters
    public int getIdSexPatient() {
        return idSexPatient;
    }

    public void setIdSexPatient(int idSexPatient) {
        this.idSexPatient = idSexPatient;
    }

    public int getIdHealthInsurancePatient() {
        return idHealthInsurancePatient;
    }

    public void setIdHealthInsurancePatient(int idHealthInsurancePatient) {
        this.idHealthInsurancePatient = idHealthInsurancePatient;
    }
}
