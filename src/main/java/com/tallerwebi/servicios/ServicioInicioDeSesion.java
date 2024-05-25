package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Usuario;

public interface ServicioInicioDeSesion {
    void iniciarSesion(Usuario usuario);
    void cerrarSesion();
    Usuario buscarUsuarioActivo();
}
