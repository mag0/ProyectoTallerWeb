package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.presentacion.responses.VehiculoResponse;
import com.tallerwebi.servicios.ServicioVehiculo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class ControladorRendimiento {
    private final ServicioVehiculo vehiculoService;

    public ControladorRendimiento(ServicioVehiculo vehiculoService) {
        this.vehiculoService = vehiculoService;
    }
    @RequestMapping ("/rendimiento")
    public ModelAndView irarendimiento(){

        List<Vehiculo> vehiculos= vehiculoService.getAllVehiculos();
        VehiculoResponse response = new VehiculoResponse(vehiculos);

        ModelMap model = new ModelMap();
        model.put("response", response);

        return new ModelAndView("rendimiento", model);


    }


}
