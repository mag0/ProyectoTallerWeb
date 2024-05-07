package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.FlotaDeVehiculos;
import com.tallerwebi.dominio.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorGestionVehicular {

    private FlotaDeVehiculos flotaDeVehiculos;

    @Autowired
    public ControladorGestionVehicular(FlotaDeVehiculos flotaDeVehiculos) {
        this.flotaDeVehiculos = flotaDeVehiculos;
    }

    @RequestMapping("/gestionVehicular")
    public ModelAndView irAGestionVehicular() {// Crear flota con vehículos por defecto
        ModelMap model = new ModelMap();
        if (flotaDeVehiculos.getVehiculos().isEmpty()) {
            crearFlotaPorDefecto(flotaDeVehiculos);
        }
        model.put("vehiculos", flotaDeVehiculos.getVehiculos());
        return new ModelAndView("gestionVehicular", model);
    }

    private void crearFlotaPorDefecto(FlotaDeVehiculos flotaDeVehiculos) {
        flotaDeVehiculos.agregarVehiculo(new Vehiculo("Toyota", "Corolla", "Moto", 1000, 200, 100, "2"));
        flotaDeVehiculos.agregarVehiculo(new Vehiculo("Mercedes-Benz", "Sprinter", "Auto", 1200, 180, 90, "1"));
        flotaDeVehiculos.agregarVehiculo(new Vehiculo("Volvo", "FH", "Camion", 1500, 250, 120, "4"));
        flotaDeVehiculos.agregarVehiculo(new Vehiculo("Chevrolet", "Cruze", "Auto", 1100, 190, 95, "2"));
    }
}
