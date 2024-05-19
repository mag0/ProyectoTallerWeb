package com.tallerwebi.presentacion.responses;

public class AsignarPedidoResponse {
    private Long pedidoId;
    private Integer vehiculoId;
    private String message;

    // Constructor
    public AsignarPedidoResponse(Long pedidoId, Integer vehiculoId, String message) {
        this.pedidoId = pedidoId;
        this.vehiculoId = vehiculoId;
        this.message = message;
    }

    // Getters y setters
    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Integer getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(Integer vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}