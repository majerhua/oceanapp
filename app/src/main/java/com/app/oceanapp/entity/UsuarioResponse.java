package com.app.oceanapp.entity;

public class UsuarioResponse {

    private String message;
    private String username;
    private int code;
    private String rol;


    public UsuarioResponse(String message, String username, int code, String rol) {
        this.message = message;
        this.username = username;
        this.code = code;
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
