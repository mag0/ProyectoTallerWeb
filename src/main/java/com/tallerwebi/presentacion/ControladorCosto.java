package com.tallerwebi.presentacion;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorCosto {
    @RequestMapping ("/costo")
    public ModelAndView irACosto(){
        ModelMap model = new ModelMap();
        return new ModelAndView("costo", model);

    }


}
