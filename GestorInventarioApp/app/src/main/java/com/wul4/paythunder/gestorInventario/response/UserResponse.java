// UserResponse.java
package com.wul4.paythunder.gestorInventario.response;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class UserResponse implements Serializable {
    @SerializedName("id_usuario")
    private int id;

    private String email;
    private String nombre;
    private String rol;

    // Para creación/edición
    @SerializedName("contrasena")
    private String contrasena;

    // Campos adicionales que tu endpoint espera y no quieres perder:
    @SerializedName("estado")
    private Integer estado;
    @SerializedName("fecha_alta")
    private String fechaAlta;
    @SerializedName("fecha_baja")
    private String fechaBaja;

    // ========== Getters & setters ==========
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }

    public String getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(String fechaAlta) { this.fechaAlta = fechaAlta; }

    public String getFechaBaja() { return fechaBaja; }
    public void setFechaBaja(String fechaBaja) { this.fechaBaja = fechaBaja; }


    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(rol);
    }
    public boolean isActive() {
        return estado == 1;
    }
}
