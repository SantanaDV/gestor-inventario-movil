package com.wul4.paythunder.gestorInventario.response;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Representa la parte “estanteria” en ProductoResponse.
 */
public class EstanteriaResponse implements Serializable {

    @SerializedName("id_estanteria")
    private int id;

    @SerializedName("posicion")
    private String posicion;

    @SerializedName("orientacion")
    private String orientacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(String orientacion) {
        this.orientacion = orientacion;
    }

    @Override
    public String toString() {
        return "EstanteriaResponse{" +
                "id=" + id +
                ", posicion='" + posicion + '\'' +
                ", orientacion='" + orientacion + '\'' +
                '}';
    }
}
