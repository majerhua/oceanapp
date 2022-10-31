package com.app.oceanapp.entity;

public class Procesamiento {
    private int lobosMarinos;
    private int pelicanos;
    private int numeroLance;
    private int cantidadFotos;

    public Procesamiento(int lobosMarinos, int pelicanos, int numeroLance, int cantidadFotos) {
        this.lobosMarinos = lobosMarinos;
        this.pelicanos = pelicanos;
        this.numeroLance = numeroLance;
        this.cantidadFotos = cantidadFotos;
    }

    public int getLobosMarinos() {
        return lobosMarinos;
    }

    public void setLobosMarinos(int lobosMarinos) {
        this.lobosMarinos = lobosMarinos;
    }

    public int getPelicanos() {
        return pelicanos;
    }

    public void setPelicanos(int pelicanos) {
        this.pelicanos = pelicanos;
    }

    public int getNumeroLance() {
        return numeroLance;
    }

    public void setNumeroLance(int numeroLance) {
        this.numeroLance = numeroLance;
    }

    public int getCantidadFotos() {
        return cantidadFotos;
    }

    public void setCantidadFotos(int cantidadFotos) {
        this.cantidadFotos = cantidadFotos;
    }
}
