package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.ContraseniasDistintas;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.repositorios.RepositorioUsuario;
import com.tallerwebi.servicios.ServicioRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioRegistroImpl implements ServicioRegistro {

    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioRegistroImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public void registrar(Usuario usuario) {
        if(verificarDatosUsuario(usuario)){
            throw new DatosIncompletos();
        }
        if(!usuario.getPassword().equals(usuario.getPasswordRepetida())){
            throw new ContraseniasDistintas();
        }
        if(usuario.getPassword().length()<6){
            throw new PasswordLongitudIncorrecta();
        }
        if(verificarQueNoExisteElUsuario(usuario)!=null){
            throw new UsuarioExistente();
        }
        repositorioUsuario.guardar(usuario);
    }

    private boolean verificarDatosUsuario(Usuario usuario){
      return usuario.getNombreUsuario().isEmpty()||usuario.getEmail().isEmpty()
              ||usuario.getPassword().isEmpty()||usuario.getPasswordRepetida().isEmpty();
    }

    private Usuario verificarQueNoExisteElUsuario(Usuario usuario){
        return repositorioUsuario.buscar(usuario.getNombreUsuario());
    }
}
