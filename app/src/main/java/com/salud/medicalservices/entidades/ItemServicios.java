package com.salud.medicalservices.entidades;

public class ItemServicios {

    private int imagen_logo;
    private String categoria;

    public ItemServicios(int imagen_logo, String categoria) {
        this.imagen_logo = imagen_logo;
        this.categoria = categoria;
    }

    public int getImagen_logo() {
        return imagen_logo;
    }

    public void setImagen_logo(int imagen_logo) {
        this.imagen_logo = imagen_logo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
