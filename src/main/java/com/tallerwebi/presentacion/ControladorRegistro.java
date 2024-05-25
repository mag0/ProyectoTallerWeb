package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.ContraseniasDistintas;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.servicios.ServicioRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {

    ServicioRegistro servicioRegistro;

    @Autowired
    public ControladorRegistro(ServicioRegistro servicioRegistro) {
        this.servicioRegistro = servicioRegistro;
    }

    @RequestMapping("/registro")
    public ModelAndView irARegistro(){
        ModelMap model = new ModelMap();
        model.addAttribute("usuario", new Usuario());
        return new ModelAndView("registro", model);

    }

    @RequestMapping(path = "/registrar", method = RequestMethod.POST)
    public ModelAndView registrar(@ModelAttribute("usuario") Usuario usuario) {
        ModelMap model = new ModelMap();
        try{
            servicioRegistro.registrar(usuario);
        }catch(DatosIncompletos ex){
            return registroFallido(model,"Faltan datos");
        }catch(ContraseniasDistintas ex){
            return registroFallido(model,"Las passwords no coinciden");
        }catch(PasswordLongitudIncorrecta ex){
            return registroFallido(model,"Password con longitud incorrecta");
        }catch(UsuarioExistente ex){
            return registroFallido(model,"Usuario ya existente");
        }

        return registroExitoso();
    }

    private ModelAndView registroExitoso() {
        return new ModelAndView("redirect:/index");
    }

    private ModelAndView registroFallido(ModelMap model,String mensaje) {
        model.put("error", mensaje);
        return new ModelAndView("registro", model);
    }
}
