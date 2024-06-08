package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.presentacion.responses.VehiculoResponse;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import com.tallerwebi.servicios.ServicioVehiculo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class ControladorRendimiento {
    private final ServicioMostrarVehiculos vehiculoService;

    public ControladorRendimiento(ServicioMostrarVehiculos vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @RequestMapping ("/rendimiento")
    public ModelAndView irarendimiento(){

        List<Vehiculo> vehiculos= vehiculoService.obtenerVehiculosDisponiblesPorUsuario();
        VehiculoResponse response = new VehiculoResponse(vehiculos);

        ModelMap model = new ModelMap();
        model.put("response", response);

        return new ModelAndView("rendimiento", model);


    }


}
