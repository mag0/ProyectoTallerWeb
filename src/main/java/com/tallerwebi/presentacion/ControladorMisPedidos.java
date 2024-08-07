package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Solicitud;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.dominio.enums.Estado;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosDisponibles;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.dominio.excepcion.VehiculoExistente;
import com.tallerwebi.presentacion.responses.PedidosAEnviar;
import com.tallerwebi.servicios.ServicioPedido;
import com.tallerwebi.servicios.ServicioSolicitud;
import com.tallerwebi.servicios.ServicioViaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/misPedidos")
public class ControladorMisPedidos {

    private ServicioViaje viajeService;
    private final ServicioSolicitud servicioSolicitud;
    private final ServicioPedido pedidoService;

    public ControladorMisPedidos(ServicioPedido pedidoService, ServicioSolicitud servicioSolicitud, ServicioViaje viajeService) {
        this.pedidoService = pedidoService;
        this.servicioSolicitud = servicioSolicitud;
        this.viajeService = viajeService;
    }

    @GetMapping("")
    public ModelAndView verMisPedidos(Model model) {
        List<Solicitud> solicitudes = servicioSolicitud.getAllSolicitudes();
        System.out.println(solicitudes);
        model.addAttribute("solicitudes", solicitudes);
        model.addAttribute("estado", Estado.DESPACHADO);
        return new ModelAndView("misPedidos");
    }

    @GetMapping("/detalles/{id}")  // Cambio la ruta a "/detalles/{id}"
    public ModelAndView cargarDetallesPedido(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("detallesPedido");
        try {
            Pedido pedido = pedidoService.getPedido(id);
            mav.addObject("pedido", pedido);
        } catch (Exception e) {
            mav.addObject("errorMessage", "Error al cargar detalles del pedido.");
        }
        return mav;
    }

    @RequestMapping(path = "/asignarPedido", method = RequestMethod.POST)
    public ModelAndView asignarPedido(@RequestParam("id") Long id,
                                      @RequestParam("distancia") List<Double> distancia,
                                      @RequestParam("tiempoConTrafico") List<Double> tiempoConTrafico,
                                      @RequestParam("distanciaConTrafico") List<Double> distanciaConTrafico) {
        ModelMap model = new ModelMap();

        try {
            List<Viaje> viajes = pedidoService.procesarYAsignarPedidos(id, distancia, tiempoConTrafico, distanciaConTrafico);
            model.addAttribute("viajes", viajes);
            return new ModelAndView("viajes", model);
        } catch (NoHayVehiculosDisponibles ex) {
            return new ModelAndView("redirect:/misPedidos?error=true");
        }
    }


}
