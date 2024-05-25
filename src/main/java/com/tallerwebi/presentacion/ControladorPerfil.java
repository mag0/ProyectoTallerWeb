package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.PasswordIncorrecta;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.servicios.ServicioInicioDeSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPerfil {

    ServicioInicioDeSesion servicioInicioDeSesion;

    @Autowired
    public ControladorPerfil(ServicioInicioDeSesion servicioInicioDeSesion) {
        this.servicioInicioDeSesion = servicioInicioDeSesion;
    }

    @RequestMapping("/perfil")
    public ModelAndView irAPerfil() {
        ModelMap model = new ModelMap();
        Usuario usuario = servicioInicioDeSesion.buscarUsuarioActivo();
        if(usuario == null){
            model.put("usuario", new Usuario());
        }else{
            model.put("usuario", usuario);
        }

        return new ModelAndView("perfil", model);
    }

    /*@RequestMapping(path = "/modificarPerfil", method = RequestMethod.POST)
    public ModelAndView modificarPerfil(@ModelAttribute("usuario") Usuario usuario) {
        ModelMap model = new ModelMap();
        try{
            servicioInicioDeSesion.iniciarSesion(usuario);
        }catch(UsuarioInexistente ex){
            return modificarFallido(model,"No existe ese usuario");
        }catch(PasswordIncorrecta ex){
            return modificarFallido(model,"Contraseña incorrecta");
        }

        return modificarExitoso();
    }

    private ModelAndView modificarExitoso() {
        return new ModelAndView("redirect:/perfil");
    }

    private ModelAndView modificarFallido(ModelMap model,String mensaje) {
        model.put("error", mensaje);
        return new ModelAndView("perfil", model);
    }*/
}
