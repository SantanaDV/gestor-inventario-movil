package com.wul4.paythunder.gestorInventario.entities;

import java.util.Date;

public class Tarea {

    private int id;

    private int estado;
    private String  descripcion;
    private String empleado_asignado;
    private String fecha_asignacion;
    private String fecha_finalizacion;
    private int id_categoria;


    public Tarea(int id, int estado, String descripcion, String empleado_asignado, String fecha_finalizacion, Date fecha_asignacion, int id_categoria) {
        this.id = id;
        this.estado = estado;
        this.descripcion = descripcion;
        this.empleado_asignado = empleado_asignado;
        this.fecha_finalizacion = fecha_finalizacion;
        this.fecha_asignacion = String.valueOf(fecha_asignacion);
        this.id_categoria = id_categoria;
    }

    public Tarea() {

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

    public CharSequence getFecha_asignacion() {
        return (CharSequence) fecha_asignacion;
    }



    public void setEstado(int estado) {
        this.estado = estado;
    }



    public int getId_categoria() {
        return id_categoria;
    }

    public String getEmpleado_asignado() {
        return empleado_asignado;
    }

    public void setFecha_asignacion(String fecha_asignacion) {
        this.fecha_asignacion = fecha_asignacion;
    }

    public CharSequence getFecha_finalizacion() {
        return (CharSequence) fecha_finalizacion;
    }

    public void setFecha_finalizacion(String fecha_finalizacion) {
        this.fecha_finalizacion = fecha_finalizacion;
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
