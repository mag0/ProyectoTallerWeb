package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Solicitud;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.dominio.enums.Estado;
import com.tallerwebi.presentacion.requests.AsignarPedidoRequest;
import com.tallerwebi.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
public class ControladorPedidos {
    public static final String REDIRECT_PEDIDOS = "redirect:/ofertas";
    private ServicioPedido pedidoService;
    private ServicioMostrarVehiculos servicioMostrarVehiculos;
    private ServicioVehiculo vehiculoService;
    private ServicioViaje viajeService;
    private ServicioSolicitud servicioSolicitud;
    private ServicioInicioDeSesion servicioInicioDeSesion;

    @Autowired
    public ControladorPedidos(ServicioInicioDeSesion servicioInicioDeSesion, ServicioPedido pedidoService, ServicioMostrarVehiculos servicioMostrarVehiculos, ServicioViaje viajeService, ServicioVehiculo vehiculoService, ServicioSolicitud servicioSolicitud) {
        this.pedidoService = pedidoService;
        this.servicioMostrarVehiculos = servicioMostrarVehiculos;
        this.viajeService = viajeService;
        this.vehiculoService = vehiculoService;
        this.servicioSolicitud = servicioSolicitud;
        this.servicioInicioDeSesion = servicioInicioDeSesion;
    }

    @RequestMapping("/ofertas")
    public ModelAndView mostrarPedidos() {
        List<Pedido> pedidos = pedidoService.getAllPedidosSinViaje();

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("pedidos", pedidos);
        return new ModelAndView("ofertas", modelMap);

    }

    @GetMapping("/ofertas/{id}/asignar")
    public ModelAndView asignarPedido(@PathVariable("id") Long id) {
        try{
            List<Vehiculo> vehiculos = servicioMostrarVehiculos.obtenerVehiculosDisponiblesPorUsuario();
            Pedido pedido = pedidoService.getPedido(id);

            ModelMap modelMap = new ModelMap();
            AsignarPedidoRequest asignarPedido = new AsignarPedidoRequest(pedido);

            modelMap.addAttribute("pedido", pedido);
            modelMap.addAttribute("vehiculos", vehiculos);
            modelMap.addAttribute("asignarPedido", asignarPedido);

            return new ModelAndView("asignarPedido", modelMap);
        }catch(Exception e) {
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("error", e.getMessage());
            return new ModelAndView("asignarPedido", modelMap);
        }
    }

    @PostMapping("/ofertas/{pedidoId}/asignar/{vehiculoId}")
    public ModelAndView asignarPedido(@PathVariable("pedidoId") Long pedidoId,@PathVariable("vehiculoId") Long vehiculoId,
                                      @ModelAttribute("asignarPedido") AsignarPedidoRequest asignarPedido) throws Exception {

        Pedido pedido = pedidoService.getPedido(pedidoId);
        if (!pedido.getEstado().equals(Estado.DESPACHADO)) {
            pedido.setEstado(Estado.DESPACHADO);
            Vehiculo vehiculo = vehiculoService.buscarVehiculo(vehiculoId);
            Viaje viaje = vehiculoService.cargarUnPaquete(vehiculo, pedido);
            Long viajeId = viajeService.guardar(viaje);
            ModelAndView mav = new ModelAndView("resultadoAsignacion");
            mav.addObject("successMessage", "El "+pedido.getNombre()+" se ha asignado correctamente al Viaje Nº "+ viajeId +"");
        } else {
            return new ModelAndView("error_despachar");
        }


        return new ModelAndView("resultadoAsignacion");
    }
    @GetMapping("/ofertas/detalles/{id}")
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
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Registrar un editor personalizado para el tipo LocalDate
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {/**
             +         * Este método se utiliza para convertir el valor de cadena de un parámetro de solicitud a un objeto LocalDate.
             +         * Se espera que la cadena de fecha tenga el formato: dd/MM/yyyy.
             +         * Si la cadena de fecha no tiene el formato esperado, se establece el valor como nulo.
             +         * @param text El valor de cadena del parámetro de solicitud.
             +         */
            @Override
            public void setAsText(String text) {
                try {
                    // Formato de fecha esperado: dd/MM/yyyy
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    setValue(LocalDate.parse(text, formatter));
                } catch (DateTimeParseException e) {
                    // Manejar el caso en que el formato de la fecha no es válido
                    setValue(null);
                }
            }

       /**
             +         * Este método se utiliza para convertir un objeto LocalDate a una representación de cadena.
             +         * La representación de cadena está en el formato: dd/MM/yyyy.
             +         * Si el objeto LocalDate es nulo, devuelve una cadena vacía.
             +         * @return La representación de cadena del objeto LocalDate.
             +         */
            @Override
            public String getAsText() {
                LocalDate date = (LocalDate) getValue();
                return (date != null ? date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "");
            }
        });
    }


    @PostMapping("/ofertas/formularioSolicitud")
    public ModelAndView mostrarFormularioSolicitud(@RequestParam("selectedPedidos") List<Long> pedidoIds, Model model) {
        // Obtener los pedidos seleccionados
        List<Pedido> pedidos = pedidoService.getPedidosByIds(pedidoIds);

        // Agregar los pedidos al modelo para pasarlos a la vista
        model.addAttribute("pedidos", pedidos);

        // Crear un objeto ModelAndView y agregar la vista y el modelo
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("formularioSolicitud");
        modelAndView.addObject("pedidos", pedidos);

        // Retornar el objeto ModelAndView
        return modelAndView;
    }

    @PostMapping("/ofertas/confirmarSolicitud")
    public ModelAndView confirmarSolicitud(@RequestParam("pedidoIds") List<Long> pedidoIds) {
        // Crear la solicitud
        Solicitud solicitud = new Solicitud();
        // Configurar otros detalles de la solicitud si es necesario
        solicitud.setUsuario(servicioInicioDeSesion.buscarUsuarioActivo());
        // Obtener los pedidos seleccionados y asociarlos a la solicitud
        List<Pedido> pedidos = pedidoService.getPedidosByIds(pedidoIds);
        for (Pedido pedido : pedidos) {
            if (!pedido.getEstado().equals(Estado.SOLICITADO)) {
                    pedido.setSolicitud(solicitud);
                    pedido.setEstado(Estado.SOLICITADO);
            }else {
                return new ModelAndView("error");
            }

        }

        // Guardar la solicitud y los pedidos asociados
        servicioSolicitud.guardar(solicitud);
        pedidoService.guardarTodos(pedidos);

        return new ModelAndView(REDIRECT_PEDIDOS);
    }






}




