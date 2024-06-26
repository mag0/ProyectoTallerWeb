package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.servicios.ServicioViaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class ControladorViajes {
    private ServicioViaje viajeService;

    @Autowired
    public ControladorViajes(ServicioViaje viajeService) {
        this.viajeService = viajeService;
    }

    @RequestMapping("/viajes")
    public ModelAndView irAViajes() {
        List<Viaje> viajes = viajeService.buscarTodos();

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("viajes", viajes);

        return new ModelAndView("viajes", modelMap);

    }

    @RequestMapping(path="/reprogramarViaje", method = RequestMethod.POST)
    public ModelAndView reprogramarViaje(@RequestParam ("idViaje") Long idViaje) {
        viajeService.reprogramarViaje(idViaje);

        return irAViajes();
    }
}
