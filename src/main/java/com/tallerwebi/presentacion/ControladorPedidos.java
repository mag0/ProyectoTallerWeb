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
    @Autowired
    public ControladorPedidos(ServicioPedido pedidoService, ServicioMostrarVehiculos servicioMostrarVehiculos, ServicioViaje viajeService, ServicioVehiculo vehiculoService, ServicioSolicitud servicioSolicitud) {
        this.pedidoService = pedidoService;
        this.servicioMostrarVehiculos = servicioMostrarVehiculos;
        this.viajeService = viajeService;
        this.vehiculoService = vehiculoService;
        this.servicioSolicitud = servicioSolicitud;
    }

    public ControladorPedidos(ServicioPedido pedidoServiceMock, ServicioMostrarVehiculos servicioMostrarVehiculos, ServicioViaje viajeServiceMock, ServicioVehiculo vehiculoServiceMock) {
        this.pedidoService = pedidoService;
        this.servicioMostrarVehiculos = servicioMostrarVehiculos;
        this.viajeService = viajeService;
        this.vehiculoService = vehiculoService;
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
        pedido.setEstado(Estado.DESPACHADO);
        Vehiculo vehiculo = vehiculoService.buscarVehiculo(vehiculoId);
        Viaje viaje = vehiculoService.cargarUnPaquete(vehiculo, pedido);
        Long viajeId = viajeService.guardar(viaje);

        ModelAndView mav = new ModelAndView("resultadoAsignacion");
        mav.addObject("successMessage", "El "+pedido.getNombre()+" se ha asignado correctamente al Viaje Nº "+viajeId+"");

        return mav;
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
    // Mostrar página de edición de pedido
    @GetMapping("/ofertas/{id}/editar")
    public ModelAndView mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        try {
            Pedido pedido = pedidoService.buscarPorId(id);
            model.addAttribute("pedido", pedido);
            return new ModelAndView("editarPedido");
        } catch (Exception e) {
            return new ModelAndView("error");
        }
    }

    @PostMapping("/ofertas/{id}/editar")
    public ModelAndView editarPedido(@PathVariable Long id, @ModelAttribute Pedido pedido) {
        try {
            pedido.setId(id);
            pedidoService.actualizarPedido(pedido);
            return new ModelAndView(REDIRECT_PEDIDOS);
        } catch (Exception e) {
            return new ModelAndView("error");
        }
    }

    @PostMapping("/ofertas")
    public ModelAndView crearPedido( @ModelAttribute Pedido pedido) {
        try {
            pedidoService.guardarPedido(pedido);
            return new ModelAndView(REDIRECT_PEDIDOS);
        } catch (Exception e) {
            return new ModelAndView("error");
        }
    }

    @GetMapping("/ofertas/solicitar/{id}")
    public ModelAndView mostrarFormularioSolicitud(@PathVariable("id") Long id, Model model) {
        Pedido pedido = pedidoService.getPedido(id);
        model.addAttribute("pedido", pedido);
        return new ModelAndView("formularioSolicitud");
    }

    @PostMapping("/ofertas/confirmarSolicitud")
    public ModelAndView confirmarSolicitud(@RequestParam("pedidoId") Long pedidoId, Model model) {
        ModelAndView mav = new ModelAndView();
        Pedido pedido = pedidoService.getPedido(pedidoId);
        if (pedido != null && pedido.getEstado().equals(Estado.DISPONIBLE)) {
            // Verificar si el pedido ya ha sido solicitado
            if (!pedido.getEstado().equals(Estado.SOLICITADO)) {
                Solicitud solicitud = new Solicitud();
                solicitud.setPedidos(List.of(pedido));
                servicioSolicitud.guardar(solicitud);
                pedido.setEstado(Estado.SOLICITADO);
                pedidoService.actualizarPedido(pedido);
                mav.setViewName("redirect:/ofertas");
            } else {
                // El pedido ya ha sido solicitado, puedes manejar esta situación aquí
                // Por ejemplo, mostrando un mensaje de error
                model.addAttribute("errorMessage", "El pedido ya ha sido solicitado anteriormente.");
                mav.setViewName("error");
            }
        } else {
            // Manejar el caso en que el pedido no esté disponible
            model.addAttribute("errorMessage", "El pedido no está disponible para ser solicitado.");
            mav.setViewName("error");
        }
        return mav;
    }

}




