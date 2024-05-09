package com.tallerwebi.dominio;

public class Zona {
    private Long id;
    private int naftaNecesaria;
    private double distanciaDelDeposito;
    private double tiempoDemora;


    public Zona(Long id, int naftaNecesaria, double distanciaDelDeposito, double tiempoDemora) {
        this.id = id;
        this.naftaNecesaria = naftaNecesaria;
        this.distanciaDelDeposito = distanciaDelDeposito;
        this.tiempoDemora = tiempoDemora;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNaftaNecesaria() {
        return naftaNecesaria;
    }

    public void setNaftaNecesaria(int naftaNecesaria) {
        this.naftaNecesaria = naftaNecesaria;
    }

    public double getDistanciaDelDeposito() {
        return distanciaDelDeposito;
    }

    public void setDistanciaDelDeposito(double distanciaDelDeposito) {
        this.distanciaDelDeposito = distanciaDelDeposito;
    }

    public double getTiempoDemora() {
        return tiempoDemora;
    }

    public void setTiempoDemora(double tiempoDemora) {
        this.tiempoDemora = tiempoDemora;
    }
}
