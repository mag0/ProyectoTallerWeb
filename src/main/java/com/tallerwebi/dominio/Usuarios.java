package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Usuarios {

    List<Usuario1> usuarios;

    public Usuarios() {
        this.usuarios = new ArrayList<>();
    }

    public List<Usuario1> getUsuarios() {
        return this.usuarios;
    }

    public void agregarUsuario(Usuario1 usuario) {
        usuarios.add(usuario);
    }

    public boolean contieneUsuario(Usuario1 usuario) {
        return usuarios.contains(usuario);
    }

    public void eliminarUsuario(Usuario1 usuario) {
        usuarios.remove(usuario);
    }

    public Usuario1 buscarUsuario(String nombreUsuario) {
        for (Usuario1 usuario : usuarios) {
            if (usuario.getUsuario().equals(nombreUsuario)) {
                return usuario;
            }
        }
        throw new UsuarioInexistente();
    }
}
