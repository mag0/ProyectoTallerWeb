package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.FlotaDeVehiculos;
import com.tallerwebi.dominio.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControladorEliminarVehiculo {

    @Autowired
    private FlotaDeVehiculos flotaDeVehiculos;


    @RequestMapping("/eliminarVehiculo")
    public String eliminarVehiculo(@RequestParam("marca") String marca,
                                   @RequestParam("modelo") String modelo) {
        Vehiculo vehiculoAEliminar = buscarVehiculo(marca,modelo);


        flotaDeVehiculos.eliminarVehiculo(vehiculoAEliminar);
        return "redirect:/gestionVehicular";
    }

    private Vehiculo buscarVehiculo(String marca, String modelo) {
        for (Vehiculo vehiculo : flotaDeVehiculos.getVehiculos()) {
            if (vehiculo.getMarca().equals(marca) && vehiculo.getModelo().equals(modelo)) {
                return vehiculo;
            }
        }
        return null;
    }
}
