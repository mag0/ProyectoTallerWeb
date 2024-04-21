package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorGestionVehicular {
    @RequestMapping("/gestionVehicular")
    public ModelAndView irAGestionVehicular() {
        ModelMap model = new ModelMap();
        return new ModelAndView("gestionVehicular", model);
    }
}
