package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Usuario1;
import com.tallerwebi.dominio.Usuarios;

public interface ServicioInicioDeSesion {
    void iniciarSesion(Usuario1 usuario1, Usuarios usuarios);
}
