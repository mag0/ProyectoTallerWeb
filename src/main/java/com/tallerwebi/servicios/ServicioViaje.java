package com.tallerwebi.servicios;


import com.tallerwebi.dominio.Viaje;

import java.util.List;

public interface ServicioViaje {
    Viaje crearViaje(Long vehiculoId, Long zonaId, List<Long> paqueteIds);

}