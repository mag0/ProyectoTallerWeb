package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.presentacion.requests.AsignarPedidoRequest;
import com.tallerwebi.servicios.ServicioPedido;
import com.tallerwebi.servicios.ServicioVehiculo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ControladorPedidos {
    private ServicioPedido pedidoService;
    private ServicioVehiculo vehiculoService;

    private static final Logger logger = LoggerFactory.getLogger(ControladorPedidos.class);

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

    @RequestMapping("/pedidos/{id}/asignar")
    public ModelAndView asignarPedido(@PathVariable("id") Long id) {
        try{
            Vehiculo vehiculo = vehiculoService.buscarVehiculo(1);
            Pedido pedido = pedidoService.getPedido(id);

            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("pedido", pedido);
            modelMap.addAttribute("vehiculo", vehiculo);
            return new ModelAndView("asignarPedido", modelMap);
        }catch(Exception e) {
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("error", e.getMessage());
            return new ModelAndView("asignarPedido", modelMap);
        }
    }

    @PostMapping("/pedidos/{id}/asignar")
    public ModelAndView asignarPedido(@PathVariable("id") Long id, @ModelAttribute AsignarPedidoRequest request) throws Exception {
        Integer vehiculoId = request.getVehiculoId();
        Long pedidoId = id;

        // Crear la respuesta
        Vehiculo vehiculo = vehiculoService.buscarVehiculo(1);
        Long viajeId = pedidoService.agregarPedido(vehiculo, pedidoId);



        // Crear ModelAndView y agregar la respuesta al modelo
        ModelAndView mav = new ModelAndView("resultadoAsignacion");
        mav.addObject("viajeId", viajeId);
        mav.addObject("vehiculo", vehiculo);

        return mav;
    }

}