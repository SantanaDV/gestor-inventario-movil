package com.wul4.paythunder.gestorInventario.response;

import java.util.Objects;

/**
 * Representa la respuesta de /login:
 * { "token": "...", "rol": "admin", "message": "…"}
 */
public class LoginResponse {

    private String token;
    private String rol;
    private String message;

    public LoginResponse() {}

    public LoginResponse(String token, String rol, String message) {
        this.token   = token;
        this.rol     = rol;
        this.message = message;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LoginResponse)) return false;
        LoginResponse that = (LoginResponse) o;
        return Objects.equals(token, that.token)
                && Objects.equals(rol,   that.rol)
                && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, rol, message);
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", rol='"   + rol   + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
