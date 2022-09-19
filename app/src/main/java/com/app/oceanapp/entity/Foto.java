package com.app.oceanapp.entity;

public class Foto {

    private int id;
    private String url;
    private boolean select;
    private int lance_id;

    public Foto(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public Foto(int id, String url, boolean select) {
        this.id = id;
        this.url = url;
        this.select = select;
    }

    public Foto(int id, String url, int lance_id) {
        this.id = id;
        this.url = url;
        this.lance_id = lance_id;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLance_id() {
        return lance_id;
    }

    public void setLance_id(int lance_id) {
        this.lance_id = lance_id;
    }
}
