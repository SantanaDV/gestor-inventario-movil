package com.wul4.paythunder.gestorInventario.entities;

import java.io.Serializable;

public class Producto implements Serializable {
    private int id_producto;
    private String nombre;
    private int cantidad;
    private String estado;  // "activo" o "desactivado"
    private String codigoQr;
    private String url_img;
    private Categoria categoria;
    private String fechaCreacion;

    // Constructor vacío
    public Producto() {
    }

    // Constructor con parámetros (opcional)
    public Producto(int id_producto, String nombre, int cantidad, String estado, String codigoQr, String url_img, Categoria categoria) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.estado = estado;
        this.codigoQr = codigoQr;
        this.url_img = url_img;
        this.categoria = categoria;
    }

    // Getters y setters

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoQr() {
        return codigoQr;
    }

    public void setCodigoQr(String codigoQr) {
        this.codigoQr = codigoQr;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "cantidad=" + cantidad +
                ", id_producto=" + id_producto +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", codigoQr='" + codigoQr + '\'' +
                ", url_img='" + url_img + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}

