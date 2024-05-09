package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Pedidos;
import com.tallerwebi.dominio.Viaje;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ControladorViajes {
    @RequestMapping("/viajes")
    public ModelAndView irAViajes() {
        List<Viaje> viajes = new ArrayList<>();
        List<Pedidos> pedidos = new ArrayList<>();
        pedidos.add(new Pedidos("Paquete 1","Fragil", "DS225", 5, 10));
       // viajes.add(new Viaje("sara","sasa",new Date(), "5", pedidos));

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("viajes", viajes);
        return new ModelAndView("viajes", modelMap);

    }
}
