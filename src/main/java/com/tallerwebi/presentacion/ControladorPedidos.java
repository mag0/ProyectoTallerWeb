package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.presentacion.requests.AsignarPedidoRequest;
import com.tallerwebi.servicios.ServicioPedido;
import com.tallerwebi.servicios.ServicioVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ControladorPedidos {
    private ServicioPedido pedidoService;
    private ServicioVehiculo vehiculoService;

    @Autowired
    public ControladorPedidos(ServicioPedido pedidoService, ServicioVehiculo vehiculoService) {
        this.pedidoService = pedidoService;
        this.vehiculoService = vehiculoService;
    }


    @RequestMapping("/pedidos")
    public ModelAndView irAPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        Pedido pedido = pedidoService.getPedido(1L);
        pedidos.add(pedido);

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("pedido", pedido);
        return new ModelAndView("pedidos", modelMap);

    }

    @PostMapping(value ="/pedidos/{id}/asignar", consumes = "application/json", produces = "application/json")
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