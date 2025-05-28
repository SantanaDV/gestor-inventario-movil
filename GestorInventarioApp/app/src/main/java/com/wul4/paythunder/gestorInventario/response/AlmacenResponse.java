package com.wul4.paythunder.gestorInventario.response;

import com.google.gson.annotations.SerializedName;

public class AlmacenResponse {
    @SerializedName("id_almacen")
    private int id;
    private int fila;
    private int columna;
    private String ubicacion;
    // getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getFila() { return fila; }
    public void setFila(int fila) { this.fila = fila; }
    public int getColumna() { return columna; }
    public void setColumna(int columna) { this.columna = columna; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
}
