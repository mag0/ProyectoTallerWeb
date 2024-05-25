/**/

package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreUsuario;
    private String email;
    private String password;
    private String passwordRepetida;
    private String rol;
    private Boolean activo = false;

    public Usuario(){
    }

    public Usuario(String passwordRepetida, String password, String email, String nombreUsuario, String rol) {
        this.passwordRepetida = passwordRepetida;
        this.password = password;
        this.email = email;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
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
    public String getPasswordRepetida() {
        return passwordRepetida;
    }
    public void setPasswordRepetida(String passwordRepetida) {
        this.passwordRepetida = passwordRepetida;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public boolean activo() {
        return activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(email, usuario.email) && Objects.equals(password, usuario.password) && Objects.equals(rol, usuario.rol) && Objects.equals(activo, usuario.activo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, rol, activo);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rol='" + rol + '\'' +
                ", activo=" + activo +
                '}';
    }

    public void activar() {
        activo = true;
    }
}