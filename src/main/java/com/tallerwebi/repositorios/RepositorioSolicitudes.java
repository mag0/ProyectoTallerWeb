package com.tallerwebi.repositorios;

import com.tallerwebi.dominio.Solicitud;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RepositorioSolicitudes {
    void guardar(Solicitud solicitud);

    List<Solicitud> getAllSolicitudes();
}
