package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.PasswordIncorrecta;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.repositorios.RepositorioUsuario;
import com.tallerwebi.servicios.ServicioInicioDeSesion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioIniciarSesionImpl implements ServicioInicioDeSesion {

    private final RepositorioUsuario repositorioUsuario;

    public ServicioIniciarSesionImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public void iniciarSesion(Usuario usuario) {
        if(verificarQueExisteElUsuario(usuario) == null){
            throw new UsuarioInexistente();
        }
        if(!verificarQueLaPasswordEsCorrecta(usuario)){
            throw new PasswordIncorrecta();
        }
        Usuario usuarioBuscado = verificarQueExisteElUsuario(usuario);
        usuarioBuscado.setActivo(true);
        repositorioUsuario.modificar(usuarioBuscado);
    }

    @Override
    public void cerrarSesion() {
        Usuario usuarioBuscado = repositorioUsuario.buscarUsuarioActivo();
        usuarioBuscado.setActivo(false);
        repositorioUsuario.modificar(usuarioBuscado);
    }

    @Override
    public Usuario buscarUsuarioActivo() {
        return repositorioUsuario.buscarUsuarioActivo();
    }

    private boolean verificarQueLaPasswordEsCorrecta(Usuario usuario) {
        return usuario.getPassword().equals(repositorioUsuario.buscar(usuario.getNombreUsuario()).getPassword());
    }

    private Usuario verificarQueExisteElUsuario(Usuario usuario) {
        return repositorioUsuario.buscar(usuario.getNombreUsuario());
    }
}
