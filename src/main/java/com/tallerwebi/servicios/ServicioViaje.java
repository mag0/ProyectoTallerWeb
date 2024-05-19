package com.tallerwebi.servicios;


import com.tallerwebi.dominio.Viaje;

import java.util.List;

public interface ServicioViaje {
    Long guardar(Viaje viaje);

    List<Viaje> buscarTodos();
}