package com.tallerwebi.repositorios;

import com.tallerwebi.dominio.Vehiculo;

public interface RepositorioVehiculo {
    void guardar(Vehiculo vehiculo);
    Vehiculo buscar(int id);
}
