package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario1;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPerfil {
    @RequestMapping("/perfil")
    public ModelAndView irAPerfil() {
        ModelMap model = new ModelMap();
        model.put("usuario1", new Usuario1());
        return new ModelAndView("perfil", model);
    }
}
