package com.app.oceanapp.entity;

import com.app.oceanapp.entity.Puerto;

import java.util.ArrayList;
import java.util.List;

public class PuertoZarpe {

    private String nombre;
    private List<Puerto> puertoArribo;

    public PuertoZarpe(String nombre, List<Puerto> puertoArribo) {
        this.nombre = nombre;
        this.puertoArribo = puertoArribo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Puerto> getPuertoArribo() {
        return puertoArribo;
    }

    public void setPuertoArribo(List<Puerto> puertoArribo) {
        this.puertoArribo = puertoArribo;
    }
}