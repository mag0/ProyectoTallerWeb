package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Solicitud;
import com.tallerwebi.servicios.ServicioInicioDeSesion;
import com.tallerwebi.servicios.ServicioPedido;
import com.tallerwebi.servicios.ServicioSolicitud;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ControladorSeguimiento {

    private final ServicioPedido pedidoService;
    private final ServicioSolicitud servicioSolicitud;
    private final ServicioInicioDeSesion servicioInicioDeSesion;

    public ControladorSeguimiento(ServicioPedido pedidoService, ServicioSolicitud servicioSolicitud, ServicioInicioDeSesion servicioInicioDeSesion) {
        this.pedidoService = pedidoService;
        this.servicioSolicitud = servicioSolicitud;
        this.servicioInicioDeSesion = servicioInicioDeSesion;
    }

    @RequestMapping("/seguimiento")
    public ModelAndView iraseguimiento() {
        ModelMap model = new ModelMap();
        List<Solicitud> solicitudes = servicioSolicitud.getAllSolicitudes().stream()
                .filter(solicitud -> solicitud.getUsuario().equals(servicioInicioDeSesion.buscarUsuarioActivo()))
                .collect(Collectors.toList());

        model.put("solicitudes", solicitudes);
        return new ModelAndView("seguimiento", model);
    }

    @GetMapping("/buscar")
    public ModelAndView buscar() {
        ModelMap model = new ModelMap();
        return new ModelAndView("seguimiento", model);
    }
}
