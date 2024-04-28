package com.tallerwebi.dominio;

public class Vehiculo {

    private String marca;
    private String modelo;
    private String tipo;
    private final Integer KILOMETRAJEMAXIMO;
    private Integer combustible;
    private Integer resistencia;
    private Integer capacidad;

    public Vehiculo(String marca, String modelo, String tipo, Integer KILOMETRAJEMAXIMO, Integer combustible, Integer resistencia, Integer capacidad) {
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.KILOMETRAJEMAXIMO = KILOMETRAJEMAXIMO;
        this.combustible = combustible;
        this.resistencia = resistencia;
        this.capacidad = capacidad;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public Integer getKILOMETRAJEMAXIMO() {
        return KILOMETRAJEMAXIMO;
    }

    public Integer getCombustible() {
        return combustible;
    }

    public Integer getResistencia() {
        return resistencia;
    }

    public Integer getCapacidad() {
        return capacidad;
    }
}
