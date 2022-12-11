package com.app.oceanapp.entity;

import java.util.List;

public class ResponseBody {
    private int code;
    private String message;
    private List<Procesamiento> data;

    public ResponseBody(int code, String message, List<Procesamiento> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Procesamiento> getData() {
        return data;
    }

    public void setData(List<Procesamiento> data) {
        this.data = data;
    }
}
