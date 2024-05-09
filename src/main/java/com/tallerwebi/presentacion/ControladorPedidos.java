package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Pedido;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorPedidos {

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
}