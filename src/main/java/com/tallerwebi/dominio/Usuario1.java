package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String usuario;
    private String email;
    private String password;
    private String passwordRepetida;
    private String fechaDeNacimiento;
    private Long id;

    public Usuario1(String usuario, String email, String password, String passwordRepetida, String fechaDeNacimiento) {
        this.usuario = usuario;
        this.email = email;
        this.password = password;
        this.passwordRepetida = passwordRepetida;
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Usuario1() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
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

}
