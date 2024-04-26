package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Pedidos;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorPedidos {

    @RequestMapping("/pedidos")
    public ModelAndView irAPedidos() {
        List<Pedidos> pedido = new ArrayList<>();
        pedido.add(new Pedidos("Paquete 1","Fragil", "DS225", 5, 10));
        pedido.add(new Pedidos("Paquete 2","Contundente", "DX125", 3, 30));
        pedido.add(new Pedidos("Paquete 3","Normal", "MS461", 10, 70));
        pedido.add(new Pedidos("Paquete 4","Normal", "XXS345", 2, 9));
        pedido.add(new Pedidos("Paquete 5","Fragil", "KJH764", 7, 45));

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("pedido", pedido);
        return new ModelAndView("pedidos", modelMap);

    }
}
/*
    @GetMapping ("/pedidos")
        public String pedidos() {
             return "pedidos";
        }
}
*/