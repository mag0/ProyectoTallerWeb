package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Solicitud;
import com.tallerwebi.repositorios.RepositorioSolicitudes;
import com.tallerwebi.servicios.ServicioSolicitud;
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
    public void eliminar(Long id) {
        Solicitud solicitud = repositorioSolicitudes.buscar(id);
        System.out.println("solicitud encontrada");
        repositorioSolicitudes.eliminar(solicitud);
        System.out.println("solicitud eliminada");
    }

    @Override
    public List<Solicitud> getAllSolicitudes() {
       return repositorioSolicitudes.getAllSolicitudes();
    }

    @Override
    public Solicitud getSolicitudById(Long id) {
        System.out.println("hola");
        return repositorioSolicitudes.buscar(id);
    }
}
