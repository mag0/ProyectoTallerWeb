package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.VehiculoExistente;
import com.tallerwebi.servicios.ServicioAgregarVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorAgregarVehiculo {

    private ServicioAgregarVehiculo servicioAgregarVehiculo;

    @Autowired
    public ControladorAgregarVehiculo(ServicioAgregarVehiculo servicioAgregarVehiculo) {
        this.servicioAgregarVehiculo = servicioAgregarVehiculo;
    }

    @RequestMapping ("/agregarVehiculo")
    public ModelAndView irAAgregarVehiculo() {
        ModelMap model = new ModelMap();
        model.put("vehiculo", new Vehiculo());
        return new ModelAndView("agregarVehiculo", model);
    }

    @RequestMapping(path = "/agregarVehiculo", method = RequestMethod.POST)
        public ModelAndView agregarVehiculo(@ModelAttribute("vehiculo") Vehiculo vehiculo) {
        ModelMap model = new ModelMap();

        try {
            servicioAgregarVehiculo.agregarVehiculo(vehiculo);
        } catch (DatosIncompletos ex) {
            return autoNoAgregado(model, "Faltan datos");
        }catch (VehiculoExistente ex) {
            return autoNoAgregado(model, "Ya existe este vehiculo");
        }

        return autoAgregadoCorrectamente();
    }

    private ModelAndView autoAgregadoCorrectamente() {
        return new ModelAndView("redirect:/gestionVehicular");
    }

    private ModelAndView autoNoAgregado(ModelMap model, String mensaje) {
        model.put("error", mensaje);
        return new ModelAndView("agregarVehiculo", model);
    }
}