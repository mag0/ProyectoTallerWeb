package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.NoExisteFiltrado;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.servicios.ServicioEliminarVehiculo;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import com.tallerwebi.servicios.ServicioFiltrarVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorGestionVehicular {

    private final ServicioMostrarVehiculos servicioMostrarVehiculos;
    private final ServicioEliminarVehiculo servicioEliminarVehiculo;
    private final ServicioFiltrarVehiculo servicioFiltrarVehiculo;

    @Autowired
    public ControladorGestionVehicular(ServicioMostrarVehiculos servicioMostrarVehiculos, ServicioEliminarVehiculo servicioEliminarVehiculo, ServicioFiltrarVehiculo servicioFiltrarVehiculo) {
        this.servicioMostrarVehiculos = servicioMostrarVehiculos;
        this.servicioEliminarVehiculo = servicioEliminarVehiculo;
        this.servicioFiltrarVehiculo = servicioFiltrarVehiculo;
    }

    @RequestMapping("/gestionVehicular")
    public ModelAndView irAGestionVehicular() {
        ModelMap model = new ModelMap();
        try {
            servicioMostrarVehiculos.obtenerVehiculosDisponiblesPorUsuario();
        } catch (NoHayVehiculosEnLaFlota ex) {
            return mostrarListaVacia(model, "No hay vehiculos en la flota");
        }
        model.put("vehiculos", servicioMostrarVehiculos.obtenerVehiculosDisponiblesPorUsuario());
        return mostrarVehiculos(model);
    }

    @RequestMapping(path ="/eliminarVehiculo", method = RequestMethod.POST)
    public ModelAndView eliminarVehiculo(@RequestParam("id") Long id) {
        servicioEliminarVehiculo.eliminarVehiculo(id);
        return irAGestionVehicular();
    }

    @RequestMapping(path ="/filtrarVehiculos", method = RequestMethod.GET)
    public ModelAndView filtrarVehiculos(@RequestParam("tipo") String tipo, @RequestParam("marca") String marca, @RequestParam("modelo") String modelo) {
        ModelMap model = new ModelMap();
        try {
            servicioFiltrarVehiculo.obtenerVehiculosFiltrados(tipo, marca, modelo);
        } catch (NoHayVehiculosEnLaFlota ex) {
            return mostrarListaVacia(model, "No existe el vehiculo que buscas");
        } catch (NoExisteFiltrado ex) {
            model.put("sinFiltro", "Ingrese un filtro");
            model.put("vehiculos", servicioMostrarVehiculos.obtenerVehiculosDisponiblesPorUsuario());
            return new ModelAndView("gestionVehicular", model);
        }
        model.put("vehiculos", servicioFiltrarVehiculo.obtenerVehiculosFiltrados(tipo, marca, modelo));
        return mostrarVehiculos(model);
    }

    private ModelAndView mostrarVehiculos(ModelMap model) {
        return new ModelAndView("gestionVehicular", model);
    }

    private ModelAndView mostrarListaVacia(ModelMap model, String mensaje) {
        model.put("error", mensaje);
        return new ModelAndView("gestionVehicular", model);
    }
}