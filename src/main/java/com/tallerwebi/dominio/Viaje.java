package com.tallerwebi.dominio;

import java.util.Date;
import java.util.List;

public class Viaje {

    private String origen;
    private String destino;
    private Date fecha;
    private String id_usuario;
    private List<Pedidos> pedidos;

    public Viaje(String origen, String destino, Date fecha, String id_usuario, List<Pedidos> pedidos) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.id_usuario = id_usuario;
        this.pedidos = pedidos;

    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public List<Pedidos> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedidos> pedidos) {
        this.pedidos = pedidos;
    }
}
