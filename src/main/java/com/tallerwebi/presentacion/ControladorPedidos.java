package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.presentacion.requests.AsignarPedidoRequest;
import com.tallerwebi.servicios.ServicioPedido;
import com.tallerwebi.servicios.ServicioVehiculo;
import com.tallerwebi.servicios.ServicioViaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
public class ControladorPedidos {
    public static final String REDIRECT_PEDIDOS = "redirect:/pedidos";
    private ServicioPedido pedidoService;
    private ServicioVehiculo vehiculoService;
    private ServicioViaje viajeService;

    @Autowired
    public ControladorPedidos(ServicioPedido pedidoService, ServicioVehiculo vehiculoService, ServicioViaje viajeService) {
        this.pedidoService = pedidoService;
        this.vehiculoService = vehiculoService;
        this.viajeService = viajeService;
    }


    @RequestMapping("/pedidos")
    public ModelAndView irAPedidos() {
        List<Pedido> pedidos = pedidoService.getAllPedidosSinViaje();

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("pedidos", pedidos);
        return new ModelAndView("pedidos", modelMap);

    }

    @GetMapping("/pedidos/{id}/asignar")
    public ModelAndView asignarPedido(@PathVariable("id") Long id) {
        try{
            List<Vehiculo> vehiculos = vehiculoService.getAllVehiculos();
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

    @PostMapping("/pedidos/{pedidoId}/asignar/{vehiculoId}")
    public ModelAndView asignarPedido(@PathVariable("pedidoId") Long pedidoId,@PathVariable("vehiculoId") Long vehiculoId, @ModelAttribute("asignarPedido") AsignarPedidoRequest asignarPedido) throws Exception {
        Pedido pedido = pedidoService.getPedido(pedidoId);
        Vehiculo vehiculo = vehiculoService.buscarVehiculo(vehiculoId);
        Viaje viaje = vehiculoService.cargarUnPaquete(vehiculo, pedido);
        Long viajeId = viajeService.guardar(viaje);

        ModelAndView mav = new ModelAndView("resultadoAsignacion");
        mav.addObject("successMessage", "El "+pedido.getNombre()+" se ha asignado correctamente al Viaje Nº "+viajeId+"");

        return mav;
    }
    @GetMapping("/pedidos/cancelar/{id}")
    public ModelAndView confirmarCancelacion(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("confirmar_cancelacion");
        modelAndView.addObject("pedidoId", id);
        return modelAndView;
    }

    @PostMapping("/pedidos/cancelar/{id}")
    public ModelAndView cancelarPedido(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            boolean isDeleted = pedidoService.eliminarPedido(id);
            ModelAndView modelAndView = new ModelAndView(REDIRECT_PEDIDOS);
            if (isDeleted) {
                redirectAttributes.addFlashAttribute("mensaje", "Pedido eliminado con éxito.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Error al eliminar el pedido.");
            }
            return modelAndView;
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el pedido.");
            return new ModelAndView(REDIRECT_PEDIDOS);
        }

    }


    @GetMapping
    public String listarPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.obtenerTodosLosPedidos();
        model.addAttribute("pedidos", pedidos);
        return "pedidos";
    }

    @GetMapping("/pedidos/{id}/reprogramar-pedido")
    public ModelAndView mostrarFormularioReprogramacion(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("reprogramar-pedido");
        try {
            Pedido pedido = pedidoService.buscarPorId(id);
            mav.addObject("pedido", pedido);
        } catch (Exception e) {
            mav.addObject("errorMessage", "El pedido" + id + "no existe");
        }
        return mav;
    }

    // Método para procesar el formulario de reprogramación
    @PostMapping("/pedidos/{id}/reprogramar-pedido")
    public ModelAndView reprogramarPedido(@PathVariable("id") Long id, @RequestParam("nuevaFecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate nuevaFecha) {

       try {
           pedidoService.reprogramarFecha(id, nuevaFecha);
           return new ModelAndView(REDIRECT_PEDIDOS);
       } catch (Exception e) {
           ModelAndView mav = new ModelAndView();
            mav.addObject("error", "El pedido" + id + "no se puede reprogramar");
       }
        return new ModelAndView(REDIRECT_PEDIDOS);
    }
    @GetMapping("/pedidos/detalles/{id}")
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
    @GetMapping("/pedidos/{id}/editar")
    public ModelAndView mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.buscarPorId(id);
        model.addAttribute("pedido", pedido);
        return new ModelAndView("editarPedido");
    }

    // Procesar solicitud de edición de pedido
    @PostMapping("/pedidos/{id}/editar")
    public ModelAndView editarPedido(@PathVariable Long id, @ModelAttribute Pedido pedido) {
        pedido.setId(id); // Aseguramos que el pedido tenga el ID correcto
        pedidoService.actualizarPedido(pedido);
        return new ModelAndView(REDIRECT_PEDIDOS);
    }
}
