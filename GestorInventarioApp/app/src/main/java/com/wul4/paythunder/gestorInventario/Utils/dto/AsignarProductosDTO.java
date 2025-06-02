package com.wul4.paythunder.gestorInventario.utils.dto;

import java.util.List;

public class AsignarProductosDTO {
    private Integer id_estanteria;
    private List<Integer> ids_producto;

    public AsignarProductosDTO(Integer id_estanteria, List<Integer> ids_producto) {
        this.id_estanteria = id_estanteria;
        this.ids_producto = ids_producto;
    }

    public Integer getId_estanteria() {
        return id_estanteria;
    }

    public void setId_estanteria(Integer id_estanteria) {
        this.id_estanteria = id_estanteria;
    }

    public List<Integer> getIds_producto() {
        return ids_producto;
    }

    public void setIds_producto(List<Integer> ids_producto) {
        this.ids_producto = ids_producto;
    }
}
