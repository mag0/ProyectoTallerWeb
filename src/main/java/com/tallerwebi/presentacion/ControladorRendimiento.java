package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.presentacion.responses.VehiculoResponse;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import com.tallerwebi.servicios.ServicioViaje;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;


@Controller
public class ControladorRendimiento {

    private final ServicioMostrarVehiculos vehiculoService;
    private final ServicioViaje viajeService;

    public ControladorRendimiento(ServicioMostrarVehiculos vehiculoService, ServicioViaje viajeService) {
           this.vehiculoService = vehiculoService;
           this.viajeService = viajeService;
    }

    @RequestMapping ("/rendimiento")
    public ModelAndView irarendimiento(){

        List<Vehiculo> vehiculos = vehiculoService.obtenerVehiculosDisponiblesPorUsuario();
        Map<String, Long> viajesPorVehiculo = viajeService.obtenerViajesPorVehiculo();

        VehiculoResponse response = new VehiculoResponse(vehiculos);

        ModelMap model = new ModelMap();
        model.put("response", response);
        model.put("viajesPorVehiculo", viajesPorVehiculo);
        model.put("vehiculos", vehiculos);

        return new ModelAndView("rendimiento", model);
    }


}
