package com.aponte.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "Perfiles")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String perfil;

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getPerfil() {return perfil;}
    public void setPerfil(String perfil) {this.perfil = perfil;}

    @Override
    public String toString() {
        return "Perfil{" +
                "id=" + id +
                ", perfil='" + perfil + '\'' +
                '}';
    }
}
