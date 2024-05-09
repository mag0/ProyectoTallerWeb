package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorGestionVehicular {

    private final ServicioMostrarVehiculos servicioMostrarVehiculos;

    @Autowired
    public ControladorGestionVehicular(ServicioMostrarVehiculos servicioMostrarVehiculos) {
        this.servicioMostrarVehiculos = servicioMostrarVehiculos;
    }

    @RequestMapping("/gestionVehicular")
    public ModelAndView irAGestionVehicular() {
        ModelMap model = new ModelMap();
        try {
            servicioMostrarVehiculos.mostrarFlota();
        } catch (NoHayVehiculosEnLaFlota ex) {
            return mostrarListaVacia(model,"No hay vehiculos en la flota");
        }
        model.put("vehiculos", servicioMostrarVehiculos.mostrarFlota());
        return mostrarVehiculos(model);
    }

    private ModelAndView mostrarVehiculos(ModelMap model) {
        return new ModelAndView("gestionVehicular", model);
    }

    private ModelAndView mostrarListaVacia(ModelMap model, String mensaje) {
        model.put("error", mensaje);
        return new ModelAndView("gestionVehicular", model);
    }
}
