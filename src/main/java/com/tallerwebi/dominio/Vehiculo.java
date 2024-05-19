package com.tallerwebi.dominio;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marca;
    private String modelo;
    private String tipo;
    private int kilometrajeMaximo;
    private int combustible;
    private int resistencia;
    private int capacidad;
    private String patente;


    public Vehiculo(){}

    public Vehiculo(String patente,String marca, String modelo, String tipo, int kilometrajeMaximo, int combustible, int resistencia, int capacidad) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.kilometrajeMaximo = kilometrajeMaximo;
        this.combustible = combustible;
        this.resistencia = resistencia;
        this.capacidad = capacidad;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
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

    public void setCapacidad(Integer capacidad) {
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

    public int getCapacidad() {
        return capacidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return Objects.equals(patente, vehiculo.patente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patente);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
