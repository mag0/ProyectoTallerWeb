package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.dominio.enums.Estado;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosDisponibles;
import com.tallerwebi.repositorios.RepositorioPedido;
import com.tallerwebi.repositorios.RepositorioUsuario;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.repositorios.RepositorioViaje;
import com.tallerwebi.servicios.ServicioPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
@Transactional
public class ServicioPedidoImpl implements ServicioPedido {
    private RepositorioPedido pedidoRepository;
    private RepositorioViaje viajeRepository;
    private RepositorioVehiculo vehiculoRepository;
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioPedidoImpl(RepositorioPedido pedidoRepository, RepositorioViaje viajeRepository,
                              RepositorioVehiculo vehiculoRepository, RepositorioUsuario repositorioUsuario) {
        this.pedidoRepository = pedidoRepository;
        this.viajeRepository = viajeRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public void asignarPedidos(List<Pedido> pedidos){
        List<Vehiculo> vehiculosDisponibles = vehiculoRepository.buscarTodosLosDisponiblesPorUsuario(repositorioUsuario.buscarUsuarioActivo().getId());
        Vehiculo vehiculoCargado = null;
        List<Pedido> pedidosOrdenados = ordenarPedidosPorZona(pedidos);

        System.out.println("Vehículos disponibles: " + vehiculosDisponibles);
        for (Pedido actualPedido : pedidosOrdenados) {
            List<Pedido> pedidosAEnviar = new ArrayList<>();

            System.out.println("Procesando pedido: " + actualPedido);

            if (hayMuchaDemora(actualPedido)) {
                System.out.println("El pedido tiene mucha demora.");
                Vehiculo vehiculoMoto = hayMotoYEntraElPedido(vehiculosDisponibles, actualPedido);
                Vehiculo vehiculoAuto = hayAutoYEntraElPedido(vehiculosDisponibles, actualPedido);
                Vehiculo vehiculoCamion = hayCamionYEntraElPedido(vehiculosDisponibles, actualPedido);

                System.out.println("Vehículo Moto: " + vehiculoMoto);
                System.out.println("Vehículo Auto: " + vehiculoAuto);
                System.out.println("Vehículo Camión: " + vehiculoCamion);

                if (vehiculoMoto != null) {
                    vehiculoCargado = vehiculoMoto;
                } else if (vehiculoAuto != null) {
                    vehiculoCargado = vehiculoAuto;
                } else if (vehiculoCamion != null) {
                    vehiculoCargado = vehiculoCamion;
                } else {
                    throw new NoHayVehiculosDisponibles();
                }

            } else {
                System.out.println("El pedido no tiene mucha demora.");
                Vehiculo vehiculoDisponible = getVehiculoDisponible(vehiculosDisponibles, actualPedido);

                System.out.println("Vehículo disponible: " + vehiculoDisponible);
                if (vehiculoDisponible != null) {
                    vehiculoCargado = vehiculoDisponible;
                } else {
                    throw new NoHayVehiculosDisponibles();
                }

            }
            System.out.println("Vehículo cargado: " + vehiculoCargado);
            actualizarVehiculo(vehiculoCargado, actualPedido);
            pedidosAEnviar.add(actualPedido);
            System.out.println("Pedidos a enviar: " + pedidosAEnviar);
            Viaje viaje = new Viaje(actualPedido.getDestino().getId(), vehiculoCargado, pedidosAEnviar, actualPedido.getFecha());
            System.out.println("Viaje: " + viaje);
            viajeRepository.guardar(viaje);
            System.out.println("Viaje guardado");
        }
        System.out.println("Fin");
    }

    public List<Pedido> ordenarPedidosPorZona(List<Pedido> pedidos) {
        pedidos.sort(Comparator.comparing(p -> p.getDestino().getId()));
        return pedidos;
    }

    public Vehiculo getVehiculoDisponible(List<Vehiculo> vehiculos, Pedido pedido){
        for (Vehiculo vehiculo : vehiculos) {
            if(vehiculo.getResistencia() > pedido.getPeso() && vehiculo.getCapacidad() > pedido.getTamanio()){
                System.out.println("Vehículo encontrado para pedido: " + vehiculo);
                return vehiculo;
            }
        }
        return null;
    }

    private boolean hayMuchaDemora(Pedido pedido){
        return pedido.getDistancia() < pedido.getDistanciaConTrafico();
    }

    private void actualizarVehiculo(Vehiculo vehiculo, Pedido pedido){
        System.out.println("Actualizando vehículo: " + vehiculo + " con pedido: " + pedido);
        vehiculo.setStatus(false);
        vehiculoRepository.actualizar(vehiculo);
        pedido.setEstado(Estado.DESPACHADO);
        pedidoRepository.modificar(pedido);
        vehiculo.setResistencia(vehiculo.getResistencia() - pedido.getPeso());
        vehiculo.setCapacidad(vehiculo.getCapacidad() - pedido.getTamanio());
    }

    private Vehiculo hayMotoYEntraElPedido(List<Vehiculo> vehiculos, Pedido pedido){
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getTipo().equals("Moto") && vehiculo.getResistencia() > pedido.getPeso() && vehiculo.getCapacidad() > pedido.getTamanio()) {
                System.out.println("Moto disponible: " + vehiculo);
                return vehiculo;
            }
        }
        return null;
    }

    private Vehiculo hayAutoYEntraElPedido(List<Vehiculo> vehiculos, Pedido pedido){
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getTipo().equals("Auto") && vehiculo.getResistencia() > pedido.getPeso() && vehiculo.getCapacidad() > pedido.getTamanio()) {
                System.out.println("Auto disponible: " + vehiculo);
                return vehiculo;
            }
        }
        return null;
    }

    private Vehiculo hayCamionYEntraElPedido(List<Vehiculo> vehiculos, Pedido pedido){
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getTipo().equals("Camion") && vehiculo.getResistencia() > pedido.getPeso() && vehiculo.getCapacidad() > pedido.getTamanio()) {
                System.out.println("Camión disponible: " + vehiculo);
                return vehiculo;
            }
        }
        return null;
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

            Viaje viaje = new Viaje(1L,vehiculo,pedidosList, pedido.getFecha());
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

    @Override
    public Long guardarPedido(Pedido pedido) {
      return pedidoRepository.guardar(pedido);
    }

    @Override
    public List<Pedido> getPedidosByIds(List<Long> pedidoIds) {
        return pedidoRepository.getPedidosByIds(pedidoIds);
    }

    @Override
    public void guardarTodos(List<Pedido> pedidos) {
        pedidoRepository.guardarTodos(pedidos);
    }

    @Override
    public List<Pedido> obtenerPedidosPorSolicitud(Long idSolicitud) {
        return pedidoRepository.obtenerPedidosPorSolicitud(idSolicitud);
    }

}