package com.wul4.paythunder.gestorInventario.entities;

import java.io.Serializable;
import java.util.Date;

public class Tarea implements Serializable{

    private int id;

    private String  descripcion;
    private String empleado_asignado;
    private String estado;

    private String fecha_asignacion;
    private String fecha_finalizacion;
    private Integer id_categoria;

    public Tarea(int id, String descripcion, String empleado_asignado, String estado, String fecha_asignacion, String fecha_finalizacion, int id_categoria) {
        this.id = id;
        this.descripcion = descripcion;
        this.empleado_asignado = empleado_asignado;
        this.estado = estado;
        this.fecha_asignacion = fecha_asignacion;
        this.fecha_finalizacion = fecha_finalizacion;
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

    public String getFecha_asignacion() {
        return  fecha_asignacion;
    }



    public void setEstado(String estado) {
        this.estado = estado;
    }



    public Integer getId_categoria() {
        return id_categoria;
    }

    public String getEmpleado_asignado() {
        return empleado_asignado;
    }

    public void setFecha_asignacion(String fecha_asignacion) {
        this.fecha_asignacion = fecha_asignacion;
    }

    public String getFecha_finalizacion() {
        return fecha_finalizacion;
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


    public String  getEstado() {
        return estado ;
    }


    public void setEstadoTexto(String estado) {
        this.estado = estado;
    }
}
