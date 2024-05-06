package com.tallerwebi.dominio;
import java.util.Objects;

public class Vehiculo {

    private String marca;
    private String modelo;
    private String tipo;
    private int kilometrajeMaximo;
    private int combustible;
    private int resistencia;
    private String capacidad;

    public Vehiculo(){}

    public Vehiculo(String marca, String modelo, String tipo, int kilometrajeMaximo, int combustible, int resistencia, String capacidad) {
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.kilometrajeMaximo = kilometrajeMaximo;
        this.combustible = combustible;
        this.resistencia = resistencia;
        this.capacidad = capacidad;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setKilometrajeMaximo(int kilometrajeMaximo) {
        this.kilometrajeMaximo = kilometrajeMaximo;
    }

    public void setCombustible(int combustible) {
        this.combustible = combustible;
    }

    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }

    public void setCapacidad(String capacidad) {
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

    public int getKilometrajeMaximo() {
        return kilometrajeMaximo;
    }

    public int getCombustible() {
        return combustible;
    }

    public int getResistencia() {
        return resistencia;
    }

    public String getCapacidad() {
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
