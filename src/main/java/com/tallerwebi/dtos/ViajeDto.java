package com.tallerwebi.dtos;

import java.util.List;

public class ViajeDto {
    private Long id;
    private List<PedidoDto> pedidos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PedidoDto> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoDto> pedidos) {
        this.pedidos = pedidos;
    }
}