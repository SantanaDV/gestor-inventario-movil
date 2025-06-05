package com.wul4.paythunder.gestorInventario.response;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Representa la respuesta JSON del endpoint /api/producto (un producto).
 * Mapea correctamente “id_producto” a “id”.
 */
public class ProductoResponse implements Serializable {

    @SerializedName("id_producto")
    private int id;                    // Este será el “ID real” del producto

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("estado")
    private String estado;             // “activo” o “desactivado”

    @SerializedName("codigoQr")
    private String codigoQr;

    @SerializedName("url_img")
    private String urlImg;

    // puede ser null en el JSON
    @SerializedName("balda")
    private Integer balda;

    // “estanteria” viene anidado
    @SerializedName("estanteria")
    private EstanteriaResponse estanteria;

    @SerializedName("categoria")
    private CategoriaResponse categoria;

    // Para completar información “posicion” si quisiera, etc.
    @SerializedName("posicion")
    private String posicion;

    @SerializedName("nfc_id")
    private String nfcId;

    @SerializedName("fecha_creacion")
    private String fechaCreacion;

    // ====== GETTERS Y SETTERS ======

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

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public Integer getBalda() {
        return balda;
    }

    public void setBalda(Integer balda) {
        this.balda = balda;
    }

    public EstanteriaResponse getEstanteria() {
        return estanteria;
    }

    public void setEstanteria(EstanteriaResponse estanteria) {
        this.estanteria = estanteria;
    }

    public CategoriaResponse getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaResponse categoria) {
        this.categoria = categoria;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getNfcId() {
        return nfcId;
    }

    public void setNfcId(String nfcId) {
        this.nfcId = nfcId;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "ProductoResponse{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", estado='" + estado + '\'' +
                ", urlImg='" + urlImg + '\'' +
                ", balda=" + balda +
                ", estanteria=" + estanteria +
                ", categoria=" + categoria +
                '}';
    }
}
