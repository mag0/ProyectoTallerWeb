package com.tallerwebi.dominio;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FlotaDeVehiculos {

    List<Vehiculo> vehiculos;

    public FlotaDeVehiculos() {
        this.vehiculos = new ArrayList<>();
    }

    public List<Vehiculo> getVehiculos() {
        return this.vehiculos;
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
    }

    public boolean contieneVehiculo(Vehiculo vehiculo) {
        return vehiculos.contains(vehiculo);
    }

    public void eliminarVehiculo(Vehiculo vehiculo) {
        vehiculos.remove(vehiculo);
    }
}
