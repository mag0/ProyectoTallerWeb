package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Usuario;
import org.hsqldb.rights.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller //Indica que esto ya es un controlador, si no se lo pone el servidor no lo toma.
public class ControladorPrueba {


    @RequestMapping("/irSaludo") //Asigna solicitudes web a clases de controlador específicas y/o métodos de controlador
    public ModelAndView saludar () {
        Usuario usuario = new Usuario();
        usuario.setEmail("angel@gmail.com");
        // Manera de pasar datos del controlador a la vista
        ModelMap modelo =  new ModelMap();
        modelo.put("dia", "11");
        modelo.put("mes", 4);
        modelo.put("usuario", usuario);
        return new ModelAndView("saludo", modelo);


}

}
