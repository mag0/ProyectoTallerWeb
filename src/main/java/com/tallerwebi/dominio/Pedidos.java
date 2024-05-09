package com.tallerwebi.dominio;

public class Pedidos {
    private String nombre;
    private String tipo;
    private String codigo;
    private Integer tamanio;
    private Integer peso;
    private Long id;


    public Pedidos(String nombre, String tipo, String codigo, Integer tamanio, Integer peso) {
       this.nombre = nombre;
        this.tipo = tipo;
        this.codigo = codigo;
        this.tamanio = tamanio;
        this.peso = peso;
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
}
