package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorPedidos {

    @GetMapping ("/pedidos")
        public String pedidos() {
             return "pedidos";
        }
}
