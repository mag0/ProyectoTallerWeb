package com.tallerwebi.presentacion.requests;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;

public class AsignarPedidoRequest {
    private Vehiculo vehiculo;
    private Integer vehiculoId;
    private Pedido pedido;

    public AsignarPedidoRequest() {}

    public AsignarPedidoRequest(Pedido pedido) {
        this.pedido = pedido;
    }

    public AsignarPedidoRequest(Integer vehiculoId, Pedido pedido) {
        this.vehiculoId = vehiculoId;
        this.pedido = pedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Integer getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(Integer vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

}
