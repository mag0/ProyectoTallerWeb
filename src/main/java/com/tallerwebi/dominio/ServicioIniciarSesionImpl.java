package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PasswordIncorrecta;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.servicios.ServicioInicioDeSesion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioIniciarSesionImpl implements ServicioInicioDeSesion {
    @Override
    public void iniciarSesion(Usuario1 usuario1, Usuarios usuarios) {
        if(!verificarQueExisteElUsuario(usuario1, usuarios)){
            throw new UsuarioInexistente();
        }
        if(!verificarQueLaPasswordEsCorrecta(usuario1, usuarios)){
            throw new PasswordIncorrecta();
        }
    }

    private boolean verificarQueLaPasswordEsCorrecta(Usuario1 usuario1, Usuarios usuarios) {
        return usuario1.getPassword().equals(usuarios.buscarUsuario(usuario1.getUsuario()).getPassword());
    }

    private Boolean verificarQueExisteElUsuario(Usuario1 usuario1, Usuarios usuarios) {
        return usuarios.contieneUsuario(usuario1);
    }
}
