package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.presentacion.requests.AsignarPedidoRequest;
import com.tallerwebi.servicios.ServicioPedido;
import com.tallerwebi.servicios.ServicioVehiculo;
import com.tallerwebi.servicios.ServicioViaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@RestController
public class ControladorPedidos {
    private ServicioPedido pedidoService;
    private ServicioVehiculo vehiculoService;
    private ServicioViaje viajeService;

    @Autowired
    public ControladorPedidos(ServicioPedido pedidoService, ServicioVehiculo vehiculoService, ServicioViaje viajeService) {
        this.pedidoService = pedidoService;
        this.vehiculoService = vehiculoService;
        this.viajeService = viajeService;
    }


    @RequestMapping("/pedidos")
    public ModelAndView irAPedidos() {
        List<Pedido> pedidos = pedidoService.getAllPedidosSinViaje();

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("pedidos", pedidos);

        return new ModelAndView("pedidos", modelMap);
    }

    @GetMapping("/pedidos/{id}/asignar")
    public ModelAndView asignarPedido(@PathVariable("id") Long id) {
        try{
            List<Vehiculo> vehiculos = vehiculoService.getAllVehiculos();
            Pedido pedido = pedidoService.getPedido(id);

            ModelMap modelMap = new ModelMap();
            AsignarPedidoRequest asignarPedido = new AsignarPedidoRequest(pedido);

            modelMap.addAttribute("pedido", pedido);
            modelMap.addAttribute("vehiculos", vehiculos);
            modelMap.addAttribute("asignarPedido", asignarPedido);

            return new ModelAndView("asignarPedido", modelMap);
        }catch(Exception e) {
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("error", e.getMessage());
            return new ModelAndView("asignarPedido", modelMap);
        }
    }

    @PostMapping("/pedidos/{pedidoId}/asignar/{vehiculoId}")
    public ModelAndView asignarPedido(@PathVariable("pedidoId") Long pedidoId,@PathVariable("vehiculoId") Long vehiculoId, @ModelAttribute("asignarPedido") AsignarPedidoRequest asignarPedido) throws Exception {
        Pedido pedido = pedidoService.getPedido(pedidoId);
        Vehiculo vehiculo = vehiculoService.buscarVehiculo(vehiculoId);
        Viaje viaje = vehiculoService.cargarUnPaquete(vehiculo, pedido);
        Long viajeId = viajeService.guardar(viaje);

        ModelAndView mav = new ModelAndView("resultadoAsignacion");
        mav.addObject("successMessage", "El "+pedido.getNombre()+" se ha asignado correctamente al Viaje Nº "+viajeId+"");

        return mav;
    }

}