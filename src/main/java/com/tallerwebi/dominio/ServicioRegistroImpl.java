package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ContraseniasDistintas;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import com.tallerwebi.servicios.ServicioRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioRegistroImpl implements ServicioRegistro {

    private Usuarios usuarios;

    @Autowired
    public ServicioRegistroImpl(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public void registrar(Usuario usuario) {
        if(usuario.getUsuario().isEmpty()||usuario.getEmail().isEmpty()
                ||usuario.getPassword().isEmpty()||usuario.getPasswordRepetida().isEmpty()
                ||usuario.getFechaDeNacimiento().toString().isEmpty()){
            throw new DatosIncompletos();
        }
        if(!usuario.getPassword().equals(usuario.getPasswordRepetida())){
            throw new ContraseniasDistintas();
        }
        if(usuario.getPassword().length()<6){
            throw new PasswordLongitudIncorrecta();
        }
        usuarios.agregarUsuario(usuario);
    }
}
