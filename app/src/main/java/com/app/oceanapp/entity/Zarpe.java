package com.app.oceanapp.entity;

public class Zarpe {

    private int id;
    private String nombre;
    private String matricula;

    public Zarpe(int id, String nombre, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.matricula = matricula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

}
