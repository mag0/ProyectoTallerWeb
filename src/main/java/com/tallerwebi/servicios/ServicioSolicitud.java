package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Solicitud;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServicioSolicitud {
    void guardar(Solicitud solicitud);

    List<Solicitud> getAllSolicitudes();

}
