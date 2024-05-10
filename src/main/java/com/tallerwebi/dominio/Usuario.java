package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String usuario;
    private String email;
    private String password;
    private String passwordRepetida;
    private Date fechaDeNacimiento;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Usuario() {

    }

    public Usuario(String usuario, String email, String password, String passwordRepetida, Date fechaDeNacimiento) {
        this.usuario = usuario;
        this.email = email;
        this.password = password;
        this.passwordRepetida = passwordRepetida;
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPasswordRepetida() {
        return passwordRepetida;
    }

    public void setPasswordRepetida(String passwordRepetida) {
        this.passwordRepetida = passwordRepetida;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Object getRol() {
        return null;
    }
}
