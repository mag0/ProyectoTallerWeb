package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Solicitud;
import com.tallerwebi.dominio.Pedido;
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
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ControladorPedidos {
    public static final String REDIRECT_OFERTAS = "redirect:/ofertas";
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
        // Obtener todos los pedidos que no tienen viaje asignado
        List<Pedido> pedidos = pedidoService.getAllPedidosSinViaje();

        // Agrupar los pedidos y coordenadas
        Map<String, List<Pedido>> pedidosAgrupados = pedidos.stream()
                .collect(Collectors.groupingBy(p -> p.getLatitude() + "-" + p.getLongitude()));

        // Crear un mapa de modelo y añadir los pedidos agrupados
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("pedidosAgrupados", pedidosAgrupados);

        // Retornar la vista 'ofertas' con el modelo
        return new ModelAndView("ofertas", modelMap);
    }

    // Carga los detalles de un pedido
    @GetMapping("/ofertas/detalles/{id}")
    public ModelAndView cargarDetallesPedido(@PathVariable("id") Long id) {
        // Crear un ModelAndView para la vista 'detallesPedido'
        ModelAndView mav = new ModelAndView("detallesPedido");
        try {
            // Obtener el pedido por su ID
            Pedido pedido = pedidoService.getPedido(id);
            mav.addObject("pedido", pedido);
        } catch (Exception e) {
            // En caso de error, añadir un mensaje de error al modelo
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
        // Asignar el usuario activo a la solicitud
        solicitud.setUsuario(servicioInicioDeSesion.buscarUsuarioActivo());

        // Llamar al servicio para procesar la confirmación de la solicitud
        ModelAndView modelAndView = pedidoService.confirmarSolicitud(solicitud, pedidoIds);

        if (modelAndView == null) {
            // Operación exitosa, redirigir a una vista de éxito
            return new ModelAndView(REDIRECT_OFERTAS);
        } else {
            // Error durante el procesamiento de pedidos, mostrar vista de error
            return new ModelAndView("error");
        }
    }
}




