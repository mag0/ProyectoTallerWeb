package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Solicitud;
import com.tallerwebi.servicios.ServicioPedido;
import com.tallerwebi.servicios.ServicioSolicitud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/misPedidos")
public class ControladorMisPedidos {

    @Autowired
    private ServicioSolicitud servicioSolicitud;
    private ServicioPedido pedidoService;

    public ControladorMisPedidos(ServicioPedido pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("")
    public ModelAndView verMisPedidos(Model model) {
        List<Solicitud> solicitudes = servicioSolicitud.getAllSolicitudes();
        List<Pedido> pedidos = new ArrayList<>();

        System.out.println("Solicitudes: " + solicitudes); // Verifica las solicitudes
        model.addAttribute("solicitudes", solicitudes);
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


}
