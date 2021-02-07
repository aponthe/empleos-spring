package com.aponte.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;


import com.aponte.model.Vacante;
import com.aponte.model.Usuario;

@Entity
@Table(name = "Solicitudes")
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date fecha;
    private String comentarios;
    private String archivo;

    @OneToOne
    @JoinColumn(name = "idVacante")
    //Configurar relación uno a uno con Vacantes
    private Vacante vacante;

    @OneToOne
    @JoinColumn(name = "idUsuario")
    //Relacion uno a uno con Usuarios
    private Usuario usuario;

    public Solicitud() {

    }

    public Solicitud(Date fecha) {
        this.fecha = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public Vacante getVacante() {
        return vacante;
    }

    public void setVacante(Vacante vacante) {
        this.vacante = vacante;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", comentarios='" + comentarios + '\'' +
                ", archivo='" + archivo + '\'' +
                ", vacante=" + vacante +
                ", usuario=" + usuario +
                '}';
    }
}
