package com.e.hospi.demo.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "health_insurance")
public class HealthInsurance {

    // Atributes 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_health_insurance")
    private int idHealthInsurance;

    @Column(nullable = false, unique = true, name = "health_insurance_name")
    private String nameHealthInsurance;

    // Constructors
    public HealthInsurance() {}
    public HealthInsurance (int idHealthInsurance, String nameHealthInsurance) {
        this.idHealthInsurance = idHealthInsurance;
        this.nameHealthInsurance = nameHealthInsurance;
    }

    // Getters And Setters
    public int getIdHealthInsurance() {
        return idHealthInsurance;
    }

    public void setIdHealthInsurance(int idHealthInsurance) {
        this.idHealthInsurance = idHealthInsurance;
    }

    public String getNameHealthInsurance() {
        return nameHealthInsurance;
    }

    public void setNameHealthInsurance(String nameHealthInsurance) {
        this.nameHealthInsurance = nameHealthInsurance;
    }
}
