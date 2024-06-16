package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.servicios.ServicioPedido;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorSeguimiento {

    private final ServicioPedido pedidoService;

    public ControladorSeguimiento(ServicioPedido pedidoService) {
        this.pedidoService = pedidoService;
    }

    @RequestMapping ("/seguimiento")
    public ModelAndView iraseguimiento(){
        ModelMap model = new ModelMap();
        List<Pedido> pedidos= pedidoService.obtenerTodosLosPedidos();
        model.put("pedidos", pedidos);
        model.put("latitud", pedidos.get(0).getLatitude());
        model.put("longitud", pedidos.get(0).getLongitude());
        return new ModelAndView("seguimiento", model);

    }

    @RequestMapping (path = "/marcarMapa", method = RequestMethod.GET)
    public ModelAndView marcarMapa(@RequestParam("id") Long id){
        ModelMap model = new ModelMap();
        Pedido pedido = pedidoService.getPedido(id);
        model.put("pedidos", pedidoService.obtenerTodosLosPedidos());
        model.put("latitud", pedido.getLatitude());
        model.put("longitud", pedido.getLongitude());
        return new ModelAndView("seguimiento", model);
    }



    @GetMapping("/buscar")
    public ModelAndView buscar(){
        ModelMap model = new ModelMap();
        return new ModelAndView("seguimiento", model);

    }

}
