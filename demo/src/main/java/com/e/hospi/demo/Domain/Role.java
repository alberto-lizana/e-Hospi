package com.e.hospi.demo.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "role")
public class Role {
   
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private int idRole;

    @Column(nullable = false, unique = true, name = "name_role")
    private String nameRole;

    // Constructors
    public Role() {}
    public Role(int idRole, String nameRole) 
    {
        this.idRole = idRole;
        this.nameRole = nameRole;
    }

    // Getters And Setters
    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getNameRole() {
        return nameRole;
    }
    
    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }   
}
