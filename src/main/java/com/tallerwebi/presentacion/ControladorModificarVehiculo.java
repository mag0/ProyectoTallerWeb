package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.VehiculoExistente;
import com.tallerwebi.servicios.ServicioModificarVehiculo;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorModificarVehiculo {

    private final ServicioModificarVehiculo servicioModificarVehiculo;
    private final ServicioMostrarVehiculos servicioMostrarVehiculos;

    @Autowired
    public ControladorModificarVehiculo(ServicioModificarVehiculo servicioModificarVehiculo, ServicioMostrarVehiculos servicioMostrarVehiculos) {
        this.servicioModificarVehiculo = servicioModificarVehiculo;
        this.servicioMostrarVehiculos = servicioMostrarVehiculos;
    }

    @RequestMapping (path ="/modificarVehiculo", method = RequestMethod.POST)
    public ModelAndView modificarVehiculo(@ModelAttribute("id") Long id) {
        ModelMap model = new ModelMap();
        model.put("vehiculo", servicioMostrarVehiculos.obtenerVehiculo(id));
        return new ModelAndView("modificarVehiculo", model);
    }

    @RequestMapping(path ="/modificarVehiculoAction", method = RequestMethod.POST)
    public ModelAndView modificarVehiculoAction(@ModelAttribute("vehiculo") Vehiculo vehiculo) {
        ModelMap model = new ModelMap();
        try {
            servicioModificarVehiculo.modificarVehiculo(vehiculo);
        } catch (DatosIncompletos ex) {
            return autoNoModificadoCorrectamente(model, "Faltan datos");
        }catch (VehiculoExistente ex) {
            return autoNoModificadoCorrectamente(model, "Ya existe este vehiculo");
        }
        model.put("vehiculos", servicioMostrarVehiculos.obtenerVehiculosDisponibles());
        return new ModelAndView("gestionVehicular", model);
    }

    private ModelAndView autoNoModificadoCorrectamente(ModelMap model, String mensaje) {
        model.put("error", mensaje);
        return new ModelAndView("modificarVehiculo", model);
    }
}
