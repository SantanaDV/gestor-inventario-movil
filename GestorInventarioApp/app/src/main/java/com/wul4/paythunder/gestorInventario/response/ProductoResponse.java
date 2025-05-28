// File: app/src/main/java/com/wul4/paythunder/gestorInventario/response/ProductoResponse.java
package com.wul4.paythunder.gestorInventario.response;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class ProductoResponse {
    @SerializedName("id")
    private int id;
    private String nombre;
    private int cantidad;
    private String estado;
    @SerializedName("codigo_qr")
    private String codigoQr;
    @SerializedName("url_img")
    private String urlImg;
    @SerializedName("fecha_creacion")
    private Date fechaCreacion;
    @SerializedName("nfc_id")
    private String nfcId;
    @SerializedName("posicion_product")
    private String posicion;
    @SerializedName("balda_producto")
    private Integer balda;

    private CategoriaResponse categoria;
    private EstanteriaResponse estanteria;

    // Getters & setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCodigoQr() { return codigoQr; }
    public void setCodigoQr(String codigoQr) { this.codigoQr = codigoQr; }

    public String getUrlImg() { return urlImg; }
    public void setUrlImg(String urlImg) { this.urlImg = urlImg; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getNfcId() { return nfcId; }
    public void setNfcId(String nfcId) { this.nfcId = nfcId; }

    public String getPosicion() { return posicion; }
    public void setPosicion(String posicion) { this.posicion = posicion; }

    public Integer getBalda() { return balda; }
    public void setBalda(Integer balda) { this.balda = balda; }

    public CategoriaResponse getCategoria() { return categoria; }
    public void setCategoria(CategoriaResponse categoria) { this.categoria = categoria; }

    public EstanteriaResponse getEstanteria() { return estanteria; }
    public void setEstanteria(EstanteriaResponse estanteria) { this.estanteria = estanteria; }
}
