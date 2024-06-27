package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.Estado;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class Pedido {
    private String nombre;
    private String tipo;
    private String codigo;
    private Integer tamanio;
    private Integer peso;
    private LocalDate fecha;
    private Double distancia;
    private Double distanciaConTrafico;
    private Double tiempoConTrafico;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Viaje viaje;
    @ManyToOne
    private Zona destino;

    @ManyToOne
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    public Pedido(String nombre, String tipo, String codigo, Integer tamanio, Integer peso, LocalDate fecha, Estado estado, Zona destino) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.codigo = codigo;
        this.tamanio = tamanio;
        this.peso = peso;
        this.fecha = fecha;
        this.estado = estado;
        this.destino = destino;
    }

    public Pedido() {}


    public Solicitud getSolicitud() {
        return solicitud;
    }

    public Long getIdSolicitud() {
        return solicitud.getId();
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public String getFecha() {
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public Long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getTamanio() {
        return tamanio;
    }

    public void setTamanio(Integer tamanio) {
        this.tamanio = tamanio;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Zona getDestino() {
        return destino;
    }

    public void setDestino(Zona destino) {
        this.destino = destino;
    }

    public double getLatitude(){
       return this.destino.getLat();
    }

    public double getLongitude(){
        return this.destino.getLon();
    }

    public Double getDistanciaConTrafico() {
        return distanciaConTrafico;
    }

    public void setDistanciaConTrafico(Double distanciaConTrafico) {
        this.distanciaConTrafico = distanciaConTrafico;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public Double getTiempoConTrafico() {
        return tiempoConTrafico;
    }

    public void setTiempoConTrafico(Double tiempoConTrafico) {
        this.tiempoConTrafico = tiempoConTrafico;
    }
}
