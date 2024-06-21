package com.tallerwebi.repositorios;

import com.tallerwebi.dominio.Vehiculo;

import java.util.List;

public interface RepositorioVehiculo {
    void guardar(Vehiculo vehiculo);
    void actualizar(Vehiculo vehiculo);
    Vehiculo buscar(Long id);
    List<Vehiculo> buscarTodos();
    List<Vehiculo> buscarTodosPorUsuario(Long idUsuario);
    List<Vehiculo> buscarTodosLosDisponiblesPorUsuario(Long idUsuario);
    void eliminar(Vehiculo vehiculo);
}
