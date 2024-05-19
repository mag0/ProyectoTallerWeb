package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.dominio.excepcion.VehiculoNoTieneCapacidad;

import java.util.List;

public interface ServicioVehiculo {
    Vehiculo buscarVehiculo(Long id) throws Exception;
    List<Vehiculo> getAllVehiculos();
    Viaje cargarUnPaquete(Vehiculo vehiculo, Pedido pedido) throws VehiculoNoTieneCapacidad;
}
