package com.tallerwebi.presentacion.responses;

import com.tallerwebi.dominio.Vehiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VehiculoResponse {

    private List<Vehiculo> vehiculos;

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    // Constructor
        public VehiculoResponse(List<Vehiculo> vehiculos) {
            this.vehiculos = vehiculos;

        }

        // Getters y setters

    public List<String> getVehiculosNames() {
        return this.vehiculos.stream()
                .map(Vehiculo::getPatente)
                .collect(Collectors.toList());
    }
}
