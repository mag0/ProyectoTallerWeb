package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.List;

@Entity
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    @ManyToOne
    private Usuario usuario;

    public Solicitud(Long id,  List<Pedido> pedidos) {
        this.id = id;
        this.pedidos = pedidos;
    }

    public Solicitud() {

    }

    // Getters y setters


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "id=" + id +
                ", pedidos=" + pedidos +
                '}';
    }
}
