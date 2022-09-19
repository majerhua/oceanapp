package com.app.oceanapp.entity;

public class Lance {
    private int id;
    private String numeroLance;

    public Lance(int id, String numeroLance) {
        this.id = id;
        this.numeroLance = numeroLance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroLance() {
        return numeroLance;
    }

    public void setNumeroLance(String numeroLance) {
        this.numeroLance = numeroLance;
    }

    @Override
    public String toString() {
        return this.numeroLance;
    }
}
