package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.presentacion.requests.AsignarPedidoRequest;
import com.tallerwebi.servicios.ServicioPedido;
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

    @Autowired
    private ServicioPedido pedidoService;
    @Autowired
    private ServicioPedidoImpl servicioPedidoImpl;

    public ControladorPedidos(ServicioPedido pedidoService, ServicioVehiculo) {

        this.pedidoService = pedidoService;
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
    public ModelAndView asignarPedido(@PathVariable("id") int id, AsignarPedidoRequest request) throws IOException {

        Integer vehiculoId = request.getVehiculoId();
        int pedidoId = id;

        Vehiculo vehiculo =



        pedidoService.agregarPedido();

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("pedido", pedidoId);

        return new ModelAndView("test", modelMap);
    }

}