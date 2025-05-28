package com.wul4.paythunder.gestorInventario.response;

import com.google.gson.annotations.SerializedName;

public class EstanteriaResponse {
    @SerializedName("id_estanteria")
    private int id;
    private String posicion;
    private String orientacion;
    // getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPosicion() { return posicion; }
    public void setPosicion(String posicion) { this.posicion = posicion; }
    public String getOrientacion() { return orientacion; }
    public void setOrientacion(String orientacion) { this.orientacion = orientacion; }
}
