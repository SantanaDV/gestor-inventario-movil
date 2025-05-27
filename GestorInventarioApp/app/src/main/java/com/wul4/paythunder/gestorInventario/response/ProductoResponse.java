package com.wul4.paythunder.gestorInventario.response;

import com.google.gson.annotations.SerializedName;

public class ProductoResponse {
    private int id;
    private String nombre;
    private int cantidad;
    @SerializedName("url_img")
    private String urlImg;
    // getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public String getUrlImg() { return urlImg; }
    public void setUrlImg(String urlImg) { this.urlImg = urlImg; }
}
