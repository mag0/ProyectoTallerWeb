package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.repositorios.RepositorioViaje;
import com.tallerwebi.servicios.ServicioViaje;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ServicioViajeImpl implements ServicioViaje {

    private RepositorioViaje viajeRepository;

    @Autowired
    public ServicioViajeImpl(RepositorioViaje viajeRepository) {
           this.viajeRepository=viajeRepository;
    }

    @Override
    public Long guardar(Viaje viaje) {
        return viajeRepository.guardar(viaje);
    }

    @Override
    public List<Viaje> buscarTodos() {
        return viajeRepository.getAll();
    }

    @Override
    public Map<String, Long> obtenerViajesPorVehiculo() {
        return viajeRepository.contarViajesPorVehiculo();
    }

    @Override
    public Viaje buscarPorId(Long id) {
            return viajeRepository.getViaje(id);
    }

    @Override
    public void reprogramarViaje(Long id) {
        Viaje viaje = viajeRepository.getViaje(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Obtener la fecha actual del viaje como String y convertirla a LocalDate
        LocalDate fechaActual = LocalDate.parse(viaje.getFecha(), formatter);
        // Sumar un día a la fecha actual
        LocalDate nuevaFecha = fechaActual.plusDays(1);
        // Convertir la nueva fecha de vuelta a String
        String nuevaFechaStr = nuevaFecha.format(formatter);
        // Establecer la nueva fecha en el viaje
        viaje.setFecha(nuevaFechaStr);

        viajeRepository.actualizarViaje(viaje);
    }
}
