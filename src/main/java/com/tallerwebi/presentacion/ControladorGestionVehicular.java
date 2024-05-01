package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Vehiculo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorGestionVehicular {


    @RequestMapping("/gestionVehicular")
    public ModelAndView irAGestionVehicular() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(new Vehiculo("Toyota", "Corolla", "Moto", 1000, 200, 100, 4));
        vehiculos.add(new Vehiculo("Mercedes-Benz", "Sprinter", "Auto", 1200, 180, 90, 3));
        vehiculos.add(new Vehiculo("Volvo", "FH", "Camion", 1500, 250, 120, 5));
        vehiculos.add(new Vehiculo("Ford", "Fiesta", "Auto", 800, 150, 80, 3));
        vehiculos.add(new Vehiculo("Chevrolet", "Cruze", "Auto", 1100, 190, 95, 4));
        ModelMap model = new ModelMap();
        model.put("vehiculos", vehiculos);
        return new ModelAndView("gestionVehicular", model);
    }
}
