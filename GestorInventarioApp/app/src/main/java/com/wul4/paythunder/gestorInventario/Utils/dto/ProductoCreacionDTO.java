package com.wul4.paythunder.gestorInventario.utils.dto;

public class ProductoCreacionDTO {
    Integer cantidad;
    Integer id_categoria;
    String codigoQr;
    String estado;
    String nombre;
     String url_img;

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigoQr() {
        return codigoQr;
    }

    public void setCodigoQr(String codigoQr) {
        this.codigoQr = codigoQr;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Integer id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public ProductoCreacionDTO(Integer cantidad, String codigoQr, String estado, Integer id_categoria, String nombre, String url_img) {
        this.cantidad = cantidad;
        this.codigoQr = codigoQr;
        this.estado = estado;
        this.id_categoria = id_categoria;
        this.nombre = nombre;
        this.url_img = url_img;
    }

    public ProductoCreacionDTO() {
    }
}