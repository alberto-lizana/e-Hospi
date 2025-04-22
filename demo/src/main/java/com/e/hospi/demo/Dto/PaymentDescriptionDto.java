package com.e.hospi.demo.Dto;

public class PaymentDescriptionDto {

    private Long idAppointment;
    private Double priceAppointment;
    private Double discountAppointment;
    private Double totalPriceAppointment;

    // Constructors
    public PaymentDescriptionDto() {}
    public PaymentDescriptionDto(Long idAppointment, Double priceAppointment, 
                                 Double discountAppointment, Double totalPriceAppointment) 
    {
        this.idAppointment = idAppointment;
        this.priceAppointment = priceAppointment;
        this.discountAppointment = discountAppointment;
        this.totalPriceAppointment = totalPriceAppointment;
    }

    // Getters and Setters
    public Long getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(Long idAppointment) {
        this.idAppointment = idAppointment;
    }

    public Double getPriceAppointment() {
        return priceAppointment;
    }

    public void setPriceAppointment(Double priceAppointment) {
        this.priceAppointment = priceAppointment;
    }

    public Double getDiscountAppointment() {
        return discountAppointment;
    }

    public void setDiscountAppointment(Double discountAppointment) {
        this.discountAppointment = discountAppointment;
    }

    public Double getTotalPriceAppointment() {
        return totalPriceAppointment;
    }

    public void setTotalPriceAppointment(Double totalPriceAppointment) {
        this.totalPriceAppointment = totalPriceAppointment;
    }
}
