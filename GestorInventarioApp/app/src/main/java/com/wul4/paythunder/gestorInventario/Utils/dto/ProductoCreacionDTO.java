package com.wul4.paythunder.gestorInventario.utils.dto;

public class ProductoCreacionDTO {
    private Integer id_producto;
    private Integer cantidad;
    private Integer id_categoria;
    private String codigoQr;
    private String estado;
    private String nombre;
    private String url_img;
    private String fechaCreacion;


    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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