package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Solicitud;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServicioPedidoImpl implements ServicioPedido {
    private RepositorioPedido pedidoRepository;
    private RepositorioViaje viajeRepository;
    private RepositorioVehiculo vehiculoRepository;
    private RepositorioUsuario repositorioUsuario;
    private  ServicioSolicitudImpl servicioSolicitud;

    @Autowired
    public ServicioPedidoImpl(RepositorioPedido pedidoRepository, RepositorioViaje viajeRepository,
                              RepositorioVehiculo vehiculoRepository, RepositorioUsuario repositorioUsuario, ServicioSolicitudImpl servicioSolicitud) {
        this.pedidoRepository = pedidoRepository;
        this.viajeRepository = viajeRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.repositorioUsuario = repositorioUsuario;
        this.servicioSolicitud = servicioSolicitud;
    }

    @Override
    public List<Viaje> asignarPedidos(List<Pedido> pedidos){

        List<Vehiculo> vehiculosDisponibles = vehiculoRepository.buscarTodosLosDisponiblesPorUsuario(repositorioUsuario.buscarUsuarioActivo().getId());
        Vehiculo vehiculoCargado = null;
        List<Pedido> pedidosOrdenados = ordenarPedidosPorZona(pedidos);
        List<Viaje> viajes = new ArrayList<>();

        System.out.println("Vehículos disponibles: " + vehiculosDisponibles);
        for (Pedido actualPedido : pedidosOrdenados) {
            List<Pedido> pedidosAEnviar = new ArrayList<>();

            System.out.println("Procesando pedido: " + actualPedido);

            if (hayMuchaDemora(actualPedido)) {
                System.out.println("El pedido tiene mucha demora.");
                Vehiculo vehiculoMoto = hayMotoYEntraElPedido(vehiculosDisponibles, actualPedido);
                Vehiculo vehiculoAuto = hayAutoYEntraElPedido(vehiculosDisponibles, actualPedido);
                Vehiculo vehiculoCamion = hayCamionYEntraElPedido(vehiculosDisponibles, actualPedido);

                System.out.println("Moto disponible: " + vehiculoMoto);
                System.out.println("Auto disponible: " + vehiculoAuto);
                System.out.println("Camion disponible: " + vehiculoCamion);

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
            viajes.add(viaje);
            System.out.println("Viaje guardado");
        }



        //Agrupo los viajes que tienen mismo vehiculo y mismo destino en un solo viaje
        Map<String, Viaje> viajesAgrupados = new HashMap<>();

        for (Viaje viaje : viajes) {
            String clave = viaje.getVehiculo().getId() + "_" + viaje.getZonaId();

            if (viajesAgrupados.containsKey(clave)) {
                // Si ya existe una entrada para este vehículo y zona, agregar el pedido al viaje existente
                Viaje viajeExistente = viajesAgrupados.get(clave);
                viajeExistente.getPedidos().addAll(viaje.getPedidos());
            } else {
                // Si no existe, agregar este viaje como una nueva entrada en el mapa
                viajesAgrupados.put(clave, viaje);
            }
        }

        // Obtener la lista final de viajes agrupados
        List<Viaje> viajesFinales = new ArrayList<>(viajesAgrupados.values());

        for (Viaje viaje : viajesFinales) {
            System.out.println("guardando viaje");
            viajeRepository.guardar(viaje);
        }
        System.out.println("Fin");
        return viajesFinales;
    }

    @Override
    public void entregarPedidoDeUnaSolicitud(Pedido pedido) {
        pedido.setEstado(Estado.FINALIZADO);
        pedidoRepository.modificar(pedido);
    }

    @Override
    public ModelAndView confirmarSolicitud(Solicitud solicitud, List<Long> pedidoIds) throws Exception {
        // Obtener los pedidos seleccionados por sus IDs
        List<Pedido> pedidos = getPedidosByIds(pedidoIds);

        for (Pedido pedido : pedidos) {
            if (!pedido.getEstado().equals(Estado.SOLICITADO) && !pedido.getEstado().equals(Estado.DESPACHADO)) {
                // Asociar la solicitud al pedido y cambiar su estado
                pedido.setSolicitud(solicitud);
                pedido.setEstado(Estado.SOLICITADO);
            } else {
                // Si algún pedido ya está solicitado, devolver una vista de error
                throw new Exception("El pedido ya ha sido solicitado o despachado");
            }
        }

        // Guardar la solicitud y los pedidos asociados
        servicioSolicitud.guardar(solicitud);
        guardarTodos(pedidos);

        // Retornar null si la operación es exitosa
        return null;
    }

    @Override
    public List<Viaje> procesarYAsignarPedidos(Long id, List<Double> distancia, List<Double> tiempoConTrafico, List<Double> distanciaConTrafico) throws NoHayVehiculosDisponibles {
        List<Pedido> pedidos = obtenerPedidosPorSolicitud(id);

        // Filtrar los pedidos finalizados
        pedidos = pedidos.stream()
                .filter(pedido -> pedido.getEstado() != Estado.FINALIZADO)
                .collect(Collectors.toList());

        // Actualizar los detalles del pedido
        for (int i = 0; i < pedidos.size(); i++) {
            pedidos.get(i).setDistancia(distancia.get(i));
            pedidos.get(i).setTiempoConTrafico(tiempoConTrafico.get(i));
            pedidos.get(i).setDistanciaConTrafico(distanciaConTrafico.get(i));
        }

        // Asignar los pedidos
        return asignarPedidos(pedidos);
    }




    public List<Pedido> ordenarPedidosPorZona(List<Pedido> pedidos) {
        pedidos.sort(Comparator.comparing(p -> p.getDestino().getId()));
        return pedidos;
    }

    public Vehiculo getVehiculoDisponible(List<Vehiculo> vehiculos, Pedido pedido){
        for (Vehiculo vehiculo : vehiculos) {
            if(vehiculo.getResistencia() >= pedido.getPeso() && vehiculo.getCapacidad() >= pedido.getTamanio()){
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
        int nuevoKilometraje = (int)(vehiculo.getKilometrajeMaximo() + pedido.getDistancia());
        vehiculo.setStatus(false);
        pedido.setEstado(Estado.DESPACHADO);
        pedidoRepository.modificar(pedido);
        vehiculo.setResistencia((int)(vehiculo.getResistencia() - pedido.getPeso()));
        vehiculo.setCapacidad(vehiculo.getCapacidad() - pedido.getTamanio());
        vehiculo.setKilometrajeMaximo(nuevoKilometraje);
        vehiculoRepository.actualizar(vehiculo);
        System.out.println("Actualizando vehículo: " + vehiculo + " con pedido: " + pedido);
        System.out.println("Resistencia de: " + vehiculo + " se resto: "+ (vehiculo.getResistencia() - pedido.getPeso()) + ", resistencia " + vehiculo.getResistencia());

    }

    private Vehiculo hayMotoYEntraElPedido(List<Vehiculo> vehiculos, Pedido pedido){
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getTipo().equals("Moto") && vehiculo.getResistencia() >= pedido.getPeso() && vehiculo.getCapacidad() >= pedido.getTamanio()) {
                return vehiculo;
            }
        }
        return null;
    }

    private Vehiculo hayAutoYEntraElPedido(List<Vehiculo> vehiculos, Pedido pedido){
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getTipo().equals("Auto") && vehiculo.getResistencia() >= pedido.getPeso() && vehiculo.getCapacidad() >= pedido.getTamanio()) {
                return vehiculo;
            }
        }
        return null;
    }

    private Vehiculo hayCamionYEntraElPedido(List<Vehiculo> vehiculos, Pedido pedido){
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getTipo().equals("Camion") && vehiculo.getResistencia() >= pedido.getPeso() && vehiculo.getCapacidad() >= pedido.getTamanio()) {
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
    public List<Pedido> getPedidosByIds(List<Long> pedidoIds) throws Exception {
        try {
            return pedidoRepository.getPedidosByIds(pedidoIds);
        } catch (Exception e) {
            throw new Exception("Error al obtener los pedidos por IDs", e);
        }
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