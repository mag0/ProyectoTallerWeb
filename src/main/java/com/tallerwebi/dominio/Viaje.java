package com.tallerwebi.dominio;


import javax.persistence.*;
import java.util.List;

@Entity
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long vehiculoId;
    private String vehiculoPatente;
    private Long zonaId;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedidos_id")
    private List<Pedido> PedidosIds;

    public Viaje(Long id, Long zonaId, List<Pedido> PedidosIds, String vehiculoPatente) {
        this.id = id;
        this.vehiculoPatente = vehiculoPatente;
        this.zonaId = zonaId;
        this.PedidosIds = PedidosIds;
    }

    public Viaje() {

    }

    public String getVehiculoPatente() {
        return vehiculoPatente;
    }

    public void setVehiculoPatente(String vehiculoPatente) {
        this.vehiculoPatente = vehiculoPatente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Pedido> getPaqueteIds() {
        return PedidosIds;
    }

    public void setPaqueteIds(List<Pedido> paqueteIds) {
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