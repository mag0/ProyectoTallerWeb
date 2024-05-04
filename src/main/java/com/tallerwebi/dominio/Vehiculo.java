package com.tallerwebi.dominio;
import java.util.Objects;

public class Vehiculo {

    private String marca;
    private String modelo;
    private String tipo;
    private Integer kilometrajeMaximo;
    private Integer combustible;
    private Integer resistencia;
    private Integer capacidad;

    public Vehiculo(){}

    public Vehiculo(String marca, String modelo, String tipo, Integer kilometrajeMaximo, Integer combustible, Integer resistencia, Integer capacidad) {
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.kilometrajeMaximo = kilometrajeMaximo;
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

    public Integer getKilometrajeMaximo() {
        return kilometrajeMaximo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return Objects.equals(marca, vehiculo.marca) &&
                Objects.equals(modelo, vehiculo.modelo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marca, modelo);
    }
}
