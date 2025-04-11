package com.e.hospi.demo.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sex")
public class Sex {

    // Atributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sex")
    private int idSex;

    @Column(nullable = false, unique = true, name = "name_sex")
    private String nameSex;
    
    // Constructors
    public Sex(){}
    
    // Getters And Setters 
    public int getIdSex() {
        return idSex;
    }
    public void setIdSex(int idSex) {
        this.idSex = idSex;
    }
    public String getNameSex() {
        return nameSex;
    }
    public void setNameSex(String nameSex) {
        this.nameSex = nameSex;
    }
}
