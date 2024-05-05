package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.FlotaDeVehiculos;
import com.tallerwebi.dominio.ServicioAgregarVehiculo;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.VehiculoExistente;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ControladorAgregarVehiculoTest {
    FlotaDeVehiculos flotaDeVehiculos = new FlotaDeVehiculos();
    ServicioAgregarVehiculo servicioAgregarVehiculo = mock(ServicioAgregarVehiculo.class);
    ControladorAgregarVehiculo controladorAgregarVehiculo = new ControladorAgregarVehiculo(servicioAgregarVehiculo,flotaDeVehiculos);

    @Test
    public void conLosDatosDelVehiculoElAltaEsExistoso(){
        Vehiculo vehiculo = givenTengoLosDatosDelVehiculo();

        ModelAndView mav =  whenEnvioDatosDelVehiculoAlServicio(vehiculo);

        thenSeVaALaVistaDeGestionVehicular(mav);
    }

    private Vehiculo givenTengoLosDatosDelVehiculo() {
        return new Vehiculo("Chevrolet", "Cruze", "Auto", 1100, 190, 95, 4);
    }

    private ModelAndView whenEnvioDatosDelVehiculoAlServicio(Vehiculo vehiculo) {
        ModelAndView mav = controladorAgregarVehiculo.agregarVehiculo(vehiculo);
        return mav;
    }

    private void thenSeVaALaVistaDeGestionVehicular(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("gestionVehicular"));
    }

    @Test
    public void noSeLograDarDeAltaAlVehiculoDebidoAQueFaltanDatos() {

        Vehiculo vehiculoIncompleto = givenTengoLosDatosDelVehiculoSalvoUno();

        doThrow(DatosIncompletos.class)
                .when(servicioAgregarVehiculo)
                .agregarVehiculo(vehiculoIncompleto, flotaDeVehiculos);

        ModelAndView mav = whenEnvioDatosDelVehiculoAlServicio(vehiculoIncompleto);

        thenSeMantieneEnLaVistaDelFormulario(mav, "Faltan datos");
    }

    private Vehiculo givenTengoLosDatosDelVehiculoSalvoUno() {
        return new Vehiculo("Chevrolet", "", "auto", 1234, 190, 95, 4);
    }

    private void thenSeMantieneEnLaVistaDelFormulario(ModelAndView mav, String mensaje) {
        assertThat(mav.getViewName(), equalToIgnoringCase("agregarVehiculo"));
        assertThat(mav.getModel().get("error").toString(), equalToIgnoringCase(mensaje));
    }

    @Test
    public void noSeLograDarDeAltaAlVehiculoDebidoAQueElVehiculoYaSeEncuentraCargadoEnLaFlota(){

        Vehiculo vehiculoRepetido = givenTengoLosDatosDelVehiculo();

        doThrow(VehiculoExistente.class)
                .when(servicioAgregarVehiculo)
                .agregarVehiculo(vehiculoRepetido,flotaDeVehiculos);

        ModelAndView mav =  whenEnvioDatosDelVehiculoAlServicio(vehiculoRepetido);

        thenSeMantieneEnLaVistaDelFormulario(mav, "Ya existe este vehiculo");
    }
}
