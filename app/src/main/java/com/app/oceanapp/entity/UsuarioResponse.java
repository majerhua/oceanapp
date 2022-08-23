package com.app.oceanapp.entity;

public class UsuarioResponse {

    private String message;
    private int code;
    private String rol;

    public UsuarioResponse(String message, int code, String rol) {
        this.message = message;
        this.code = code;
        this.rol = rol;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
