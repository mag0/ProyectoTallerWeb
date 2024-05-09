package com.tallerwebi.dominio;


import java.util.List;

public class Viaje {
    private Long id;
    private Long vehiculoId;
    private Long zonaId;
    private List<Long> PedidosIds;

    public Viaje(Long id, Long vehiculoId, Long zonaId, List<Long> PedidosIds) {
        this.id = id;
        this.vehiculoId = vehiculoId;
        this.zonaId = zonaId;
        this.PedidosIds = PedidosIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getPaqueteIds() {
        return PedidosIds;
    }

    public void setPaqueteIds(List<Long> paqueteIds) {
        this.PedidosIds = paqueteIds;
    }

    public Long getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(Long vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public Long getZonaId() {
        return zonaId;
    }

    public void setZonaId(Long zonaId) {
        this.zonaId = zonaId;
    }
}