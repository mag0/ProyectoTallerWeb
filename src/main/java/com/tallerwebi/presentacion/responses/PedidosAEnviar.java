package com.tallerwebi.presentacion.responses;

import com.tallerwebi.dominio.Pedido;
import java.util.ArrayList;
import java.util.List;

public class PedidosAEnviar {

    private List<Double> distancias = new ArrayList<>();
    private List<Double> distanciasConTrafico = new ArrayList<>();
    private List<Double> tiempoConTrafico = new ArrayList<>();

    public PedidosAEnviar(){}

    // Getters y setters

    public List<Double> getDistancias() {
        return distancias;
    }

    public void setDistancias(Double distancias) {
        this.distancias.add(distancias);
    }

    public List<Double> getDistanciasConTrafico() {
        return distanciasConTrafico;
    }

    public void setDistanciasConTrafico(Double distanciasConTrafico) {
        this.distanciasConTrafico.add(distanciasConTrafico);
    }

    public List<Double> getTiempoConTrafico() {
        return tiempoConTrafico;
    }

    public void setTiempoConTrafico(Double tiempoConTrafico) {
        this.tiempoConTrafico.add(tiempoConTrafico);
    }
}
