package com.wul4.paythunder.gestorInventario.entities;

import java.util.Date;

public class Tarea {

    private int id;

    private int estado;
    private String  descripcion;
    private String empleado_asignado;
    private Date fecha_asignacion;
    private Date fecha_finalizacion;
    private int id_categoria;


    public Tarea(int id) {
    }

    public Tarea(int id, int estado, String descripcion, String empleado_asignado, Date fecha_asignacion, Date fecha_finalizacion, int id_categoria) {
        this.id = id;
        this.estado = estado;
        this.descripcion = descripcion;
        this.empleado_asignado = empleado_asignado;
        this.fecha_asignacion = fecha_asignacion;
        this.fecha_finalizacion = fecha_finalizacion;
        this.id_categoria = id_categoria;
    }

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


    public void setEmpleado_asignado(String empleado_asignado) {
        this.empleado_asignado = empleado_asignado;
    }


    public void setFecha_asignacion(Date fecha_asignacion) {
        this.fecha_asignacion = fecha_asignacion;
    }


    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setFecha_finalizacion(Date fecha_finalizacion) {
        this.fecha_finalizacion = fecha_finalizacion;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public String getEmpleado_asignado() {
        return empleado_asignado;
    }

    public Date getFecha_asignacion() {
        return fecha_asignacion;
    }

    public Date getFecha_finalizacion() {
        return fecha_finalizacion;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getEmpleadoAsignado() {
        return empleado_asignado;
    }


    public int getEstado() {
        return estado ;
    }


}
