package com.tallerwebi.presentacion.requests;

public class AsignarPedidoRequest {
    private Integer vehiculoId;

    public AsignarPedidoRequest(Integer vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public Integer getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(Integer vehiculoId) {
        this.vehiculoId = vehiculoId;
    }
}
