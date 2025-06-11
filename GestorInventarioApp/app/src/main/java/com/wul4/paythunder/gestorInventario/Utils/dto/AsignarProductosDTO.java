package com.wul4.paythunder.gestorInventario.utils.dto;

import java.util.List;
import java.util.Map;

/**
 *
 * Dto que mapea la entidad Producto con balda y lo asigna en la base de datos
 * PUT /api/producto/asignarEstanteria
 * {
 *   "id_estanteria": 42,
 *   "ids_producto": [1, 5, 8]
 * }
 */
public class AsignarProductosDTO {
    private Integer id_estanteria;
    private List<Integer> ids_producto;
    private Map<Integer,Integer> baldas;

    public AsignarProductosDTO(Integer id_estanteria, List<Integer> ids_producto, Map<Integer,Integer> baldas) {
        this.id_estanteria = id_estanteria;
        this.ids_producto = ids_producto;
        this.baldas = baldas;
    }

    public AsignarProductosDTO() {
    }

    public Integer getId_estanteria() { return id_estanteria; }
    public void setId_estanteria(Integer id_estanteria) { this.id_estanteria = id_estanteria; }
    public List<Integer> getIds_producto() { return ids_producto; }
    public void setIds_producto(List<Integer> ids_producto) { this.ids_producto = ids_producto; }
    public Map<Integer,Integer> getBaldas() { return baldas; }
    public void setBaldas(Map<Integer,Integer> baldas) { this.baldas = baldas; }
}
