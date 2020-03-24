package com.salud.medicalservices.entidades;

public class ItemProductos {


    private int imagen_logo;
    private String nombre_comercial;
    private String nombre_generico;
    private String nombre_presentacion;
    private String nombre_laboratorio;
    private String precio;

    public ItemProductos(int imagen_logo, String nombre_comercial, String nombre_generico, String nombre_presentacion, String nombre_laboratorio, String precio) {
        this.imagen_logo = imagen_logo;
        this.nombre_comercial = nombre_comercial;
        this.nombre_generico = nombre_generico;
        this.nombre_presentacion = nombre_presentacion;
        this.nombre_laboratorio = nombre_laboratorio;
        this.precio = precio;
    }

    public int getImagen_logo() {
        return imagen_logo;
    }

    public void setImagen_logo(int imagen_logo) {
        this.imagen_logo = imagen_logo;
    }

    public String getNombre_comercial() {
        return nombre_comercial;
    }

    public void setNombre_comercial(String nombre_comercial) {
        this.nombre_comercial = nombre_comercial;
    }

    public String getNombre_generico() {
        return nombre_generico;
    }

    public void setNombre_generico(String nombre_generico) {
        this.nombre_generico = nombre_generico;
    }

    public String getNombre_presentacion() {
        return nombre_presentacion;
    }

    public void setNombre_presentacion(String nombre_presentacion) {
        this.nombre_presentacion = nombre_presentacion;
    }

    public String getNombre_laboratorio() {
        return nombre_laboratorio;
    }

    public void setNombre_laboratorio(String nombre_laboratorio) {
        this.nombre_laboratorio = nombre_laboratorio;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
