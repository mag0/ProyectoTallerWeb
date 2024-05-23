package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.FlotaDeVehiculos;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.servicios.ServicioEliminarVehiculo;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorGestionVehicular {

    private final ServicioMostrarVehiculos servicioMostrarVehiculos;
    private final FlotaDeVehiculos flotaDeVehiculos;
    private final ServicioEliminarVehiculo servicioEliminarVehiculo;

    @Autowired
    public ControladorGestionVehicular(ServicioMostrarVehiculos servicioMostrarVehiculos, FlotaDeVehiculos flotaDeVehiculos, ServicioEliminarVehiculo servicioEliminarVehiculo) {
        this.servicioMostrarVehiculos = servicioMostrarVehiculos;
        this.flotaDeVehiculos = flotaDeVehiculos;
        this.servicioEliminarVehiculo = servicioEliminarVehiculo;
    }

    @RequestMapping("/gestionVehicular")
    public ModelAndView irAGestionVehicular() {
        ModelMap model = new ModelMap();
        try {
            servicioMostrarVehiculos.mostrarFlota(flotaDeVehiculos);
        } catch (NoHayVehiculosEnLaFlota ex) {
            return mostrarListaVacia(model, "No hay vehiculos en la flota");
        }
        model.put("vehiculos", flotaDeVehiculos.getVehiculos());
        return mostrarVehiculos(model);
    }

    @RequestMapping(path ="/eliminarVehiculo", method = RequestMethod.POST)
    public String eliminarVehiculo(@RequestParam("patente") String patente, ModelMap model) {
        Vehiculo vehiculo = buscarVehiculo(flotaDeVehiculos, patente);
        if (vehiculo != null) {
            servicioEliminarVehiculo.eliminarVehiculo(vehiculo, flotaDeVehiculos);
            model.put("vehiculos", flotaDeVehiculos.getVehiculos());
            return "redirect:/gestionVehicular";
        } else {
            model.put("error", "No se encontró el vehículo");
            return "gestionVehicular";
        }
    }



    private Vehiculo buscarVehiculo(FlotaDeVehiculos flotaDeVehiculos, String patente){
        return flotaDeVehiculos.buscarVehiculoPorId(patente);
    }

    private ModelAndView mostrarVehiculos(ModelMap model) {
        return new ModelAndView("gestionVehicular", model);
    }

    private ModelAndView mostrarListaVacia(ModelMap model, String mensaje) {
        model.put("error", mensaje);
        return new ModelAndView("gestionVehicular", model);
    }
}