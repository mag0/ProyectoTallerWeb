package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Solicitud;
import com.tallerwebi.repositorios.RepositorioSolicitudes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioSolicitudImpl implements ServicioSolicitud {
    private RepositorioSolicitudes repositorioSolicitudes;

    public ServicioSolicitudImpl(RepositorioSolicitudes repositorioSolicitudes) {
        this.repositorioSolicitudes = repositorioSolicitudes;
    }
    @Override
    public void guardar(Solicitud solicitud) {
        repositorioSolicitudes.guardar(solicitud);
    }

    @Override
    public List<Solicitud> getAllSolicitudes() {
       return repositorioSolicitudes.getAllSolicitudes();
    }
}
