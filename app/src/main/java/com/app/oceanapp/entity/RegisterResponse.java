package com.app.oceanapp.entity;

public class RegisterResponse {

    private String message;
    private int code;
    private int id;
    private Foto data;

    public RegisterResponse(String message, int code, int id) {
        this.message = message;
        this.code = code;
        this.id = id;
    }

    public RegisterResponse(String message, int code, int id, Foto data) {
        this.message = message;
        this.code = code;
        this.id = id;
        this.data = data;
    }

    public Foto getData() {
        return data;
    }

    public void setData(Foto data) {
        this.data = data;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
