package com.tallerwebi.dominio;


import javax.persistence.*;
import java.util.List;

@Entity
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Vehiculo vehiculo;
    private Long zonaId;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    public Viaje(Long zonaId, Vehiculo vehiculo,List<Pedido> pedidos) {
        this.vehiculo = vehiculo;
        this.zonaId = zonaId;
        this.pedidos = pedidos;
    }

    public Viaje() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Pedido> getPaqueteIds() {
        return this.pedidos;
    }

    public void setPaqueteIds(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Vehiculo getVehiculo() {
        return this.vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Long getZonaId() {
        return zonaId;
    }

    public void setZonaId(Long zonaId) {
        this.zonaId = zonaId;
    }

}