package com.tallerwebi.presentacion;


import com.tallerwebi.servicios.ServicioPedido;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorSeguimiento {

    private final ServicioPedido pedidoService;

    public ControladorSeguimiento(ServicioPedido pedidoService) {
        this.pedidoService = pedidoService;
    }


    @RequestMapping ("/seguimiento")
    public ModelAndView iraseguimiento(){
        ModelMap model = new ModelMap();
        model.put("pedido", pedidoService.getPedido(1L));

        return new ModelAndView("seguimiento", model);

    }

    @GetMapping("/buscar")
    public ModelAndView buscar(){
        ModelMap model = new ModelMap();
        return new ModelAndView("seguimiento", model);

    }

}
