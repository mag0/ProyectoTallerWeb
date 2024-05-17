package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.presentacion.requests.AsignarPedidoRequest;
import com.tallerwebi.servicios.ServicioPedido;
import com.tallerwebi.servicios.ServicioVehiculo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ControladorApi {

    private final ServicioVehiculo vehiculoService;
    private final ServicioPedido pedidoService;

    public ControladorApi(ServicioVehiculo vehiculoService, ServicioPedido pedidoService) {
        this.vehiculoService = vehiculoService;
        this.pedidoService = pedidoService;
    }

    @PostMapping(value = "/api/pedidos/{id}/asignar", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Object>> asignarPedido(@PathVariable("id") Long id, @RequestBody AsignarPedidoRequest request) {
        Integer vehiculoId = request.getVehiculoId();
        Long pedidoId = id;

        Map<String, Object> response = new HashMap<>();

        try {
            Vehiculo vehiculo = vehiculoService.buscarVehiculo(vehiculoId);
            Long viajeId = pedidoService.agregarPedido(vehiculo, pedidoId);

            response.put("pedido", viajeId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
