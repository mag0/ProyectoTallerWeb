package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Paquete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Viaje viaje;
    @OneToOne
    private Pedido pedido;


    public Paquete() {
    }

    public Paquete(Viaje viaje, Pedido pedido) {
        this.viaje = viaje;
        this.pedido = pedido;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
