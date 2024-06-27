package com.tallerwebi.dominio;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private String fecha;

    public Viaje(Long zonaId, Vehiculo vehiculo,List<Pedido> pedidos, String fecha) {
        this.vehiculo = vehiculo;
        this.zonaId = zonaId;
        this.pedidos = pedidos;
        this.fecha = fecha;
    }

    public Viaje() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Pedido> getPedidos() {
        return this.pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}