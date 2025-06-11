package com.wul4.paythunder.gestorInventario.response;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Representa la parte “categoria” en ProductoResponse.
 */
public class CategoriaResponse implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("descripcion")
    private String descripcion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "CategoriaResponse{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
