package com.wul4.paythunder.gestorInventario.entities;

import java.io.Serializable;

public class Categoria implements Serializable {
    private int id;
    private String descripcion;

    public Categoria() {
    }

    public Categoria(String descripcion, int id) {
        this.descripcion = descripcion;
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
