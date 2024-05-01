package com.tallerwebi.presentacion;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRendimiento {
    @RequestMapping ("/rendimiento")
    public ModelAndView irarendimiento(){
        ModelMap model = new ModelMap();
        return new ModelAndView("rendimiento", model);

    }


}
