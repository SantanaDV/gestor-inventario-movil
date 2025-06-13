package com.wul4.paythunder.gestorInventario.utils.dto;

public class TareaCategoriaDTO {
    private int id;
    private String descripcion;
    private String empleado_asignado;
    private String estado;
    private String fecha_asignacion;
    private String fecha_finalizacion;
    private int id_categoria;

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEmpleado_asignado() {
        return empleado_asignado;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEmpleado_asignado(String empleado_asignado) {
        this.empleado_asignado = empleado_asignado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFecha_asignacion(String fecha_asignacion) {
        this.fecha_asignacion = fecha_asignacion;
    }

    public void setFecha_finalizacion(String fecha_finalizacion) {
        this.fecha_finalizacion = fecha_finalizacion;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getFecha_asignacion() {
        return fecha_asignacion;
    }

    public String getFecha_finalizacion() {
        return fecha_finalizacion;
    }

    public int getId_categoria() {
        return id_categoria;
    }
}
