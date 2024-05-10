package com.tallerwebi.dominio;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Usuarios {

    List<Usuario> usuarios;

    public Usuarios() {
        this.usuarios = new ArrayList<>();
    }

    public List<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public boolean contieneVehiculo(Usuario usuario) {
        return usuarios.contains(usuario);
    }

    public void eliminarUsuario(Usuario usuario) {
        usuarios.remove(usuario);
    }
}
