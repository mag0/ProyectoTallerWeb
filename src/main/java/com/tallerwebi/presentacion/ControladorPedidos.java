package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.presentacion.requests.AsignarPedidoRequest;
import com.tallerwebi.servicios.ServicioPedido;
import com.tallerwebi.servicios.ServicioVehiculo;
import com.tallerwebi.servicios.impl.ServicioPedidoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@Controller
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
        List<Pedido> pedido = new ArrayList<>();
        pedido.add(new Pedido("Paquete 1","Fragil", "DS225", 5, 10, LocalDate.now()));
        pedido.add(new Pedido("Paquete 2","Contundente", "DX125", 3, 30, LocalDate.now()));
        pedido.add(new Pedido("Paquete 3","Normal", "MS461", 10, 70, LocalDate.now()));
        pedido.add(new Pedido("Paquete 4","Normal", "XXS345", 2, 9, LocalDate.now().plusDays(1)));
        pedido.add(new Pedido("Paquete 5","Fragil", "KJH764", 7, 45, LocalDate.now().plusDays(1)));

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("pedido", pedido);
        return new ModelAndView("pedidos", modelMap);

    }

    @PostMapping("/pedidos/{id}/asignar")
    @ResponseBody
    public ModelAndView asignarPedido(@PathVariable("id") int id, AsignarPedidoRequest request) {
        Integer vehiculoId = request.getVehiculoId();
        int pedidoId = id;

        try{
            Vehiculo vehiculo = vehiculoService.buscarVehiculo(vehiculoId);
            Long viajeId = pedidoService.agregarPedido(vehiculo,pedidoId);

            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("pedido", viajeId);

            return new ModelAndView("test", modelMap);
        }catch(Exception e){
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("error", e.getMessage());

            return new ModelAndView("test", modelMap);
        }

    }

}