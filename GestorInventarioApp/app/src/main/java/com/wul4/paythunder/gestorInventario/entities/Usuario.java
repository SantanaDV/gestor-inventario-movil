package com.wul4.paythunder.gestorInventario.entities;



import java.util.Date;

import java.util.Objects;


public class Usuario {

    private int id_usuario;
    private String nombre,contrasena;

    private String email;
    private int estado;

    private Date fechaAlta;
    private String fecha_baja;

    private String rol;

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = String.valueOf(fecha_baja);
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id_usuario == usuario.id_usuario && estado == usuario.estado && Objects.equals(nombre, usuario.nombre) && Objects.equals(contrasena, usuario.contrasena) && Objects.equals(email, usuario.email) && Objects.equals(fechaAlta, usuario.fechaAlta) && Objects.equals(fecha_baja, usuario.fecha_baja) && Objects.equals(rol, usuario.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_usuario, nombre, contrasena, email, estado, fechaAlta, fecha_baja, rol);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "contrasena='" + contrasena + '\'' +
                ", id_usuario=" + id_usuario +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", estado=" + estado +
                ", fechaAlta=" + fechaAlta +
                ", fecha_baja=" + fecha_baja +
                ", rol='" + rol + '\'' +
                '}';
    }
}

