package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.repositorios.RepositorioPedido;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.repositorios.RepositorioViaje;
import com.tallerwebi.servicios.ServicioPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioPedidoImpl implements ServicioPedido {
    private RepositorioPedido pedidoRepository;
    private RepositorioViaje viajeRepository;
    private RepositorioVehiculo vehiculoRepository;

    @Autowired
    public ServicioPedidoImpl(RepositorioPedido pedidoRepository, RepositorioViaje viajeRepository, RepositorioVehiculo vehiculoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.viajeRepository = viajeRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    public Long agregarPedido(Vehiculo vehiculo, Long pedidoId) throws Exception {
        List<Pedido> pedidosList = new ArrayList<>();
        Pedido pedido = this.getPedido(pedidoId);


            if(vehiculo.getCapacidad() < pedido.getPeso()){
                throw new Exception("El vehiculo no tiene la capacidad para cargar el pedido");
            }

            pedidosList.add(pedido);
            vehiculo.setCapacidad(vehiculo.getCapacidad() - pedido.getPeso());
            vehiculoRepository.actualizar(vehiculo);

            Viaje viaje = new Viaje(1L,vehiculo,pedidosList);
            pedido.setViaje(viaje);
            pedidoRepository.modificar(pedido);

            return viajeRepository.guardar(viaje);

    }

    @Override
    public Pedido getPedido(Long id) {
        return pedidoRepository.buscarPedido(id);
    }

    @Override
    public List<Pedido> getAll() {
        return pedidoRepository.buscarTodos();
    }

    @Override
    public List<Pedido> getAllPedidosSinViaje() {
        return pedidoRepository.buscarSinViajes();
    }

    @Override
    public boolean eliminarPedido(Long id) {
        return pedidoRepository.eliminarPedido(id);
    }

    @Override
    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoRepository.obtenerTodosLosPedidos();
    }

    @Override
    public Pedido buscarPorId(Long id) {
      Pedido pedido = pedidoRepository.buscarPedido(id);
        if (pedido != null){
            return pedido;
        } else {
            throw new IllegalArgumentException("pedido no existe");
        }
    }

    @Override
    public void reprogramarFecha(Long id, LocalDate nuevaFecha) {
        Pedido pedido = buscarPorId(id);
        if (pedido != null){
            pedido.setFecha(nuevaFecha);
            pedidoRepository.guardar(pedido);
        } else {
            throw new IllegalArgumentException("pedido no existe");
        }

    }

    @Override
    public void actualizarPedido(Pedido pedido) {
        pedidoRepository.modificar(pedido);
    }

}
