package com.salud.medicalservices.HelperSharePreference.entity;

public class Product {

    private int imagen_logo;
    private String nombre_comercial;
    private String nombre_generico;
    private String nombre_presentacion;
    private String nombre_laboratorio;
    private String precio;
    private String subtotal;
    private String nombre_empaque;
    private String unidades;
    private String idUnique;


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

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getNombre_empaque() {
        return nombre_empaque;
    }

    public void setNombre_empaque(String nombre_empaque) {
        this.nombre_empaque = nombre_empaque;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }

    public String getIdUnique() {
        return idUnique;
    }

    public void setIdUnique(String idUnique) {
        this.idUnique = idUnique;
    }

    @Override
    public String toString() {
        return "Product{" +
                "imagen_logo=" + imagen_logo +
                ", nombre_comercial='" + nombre_comercial + '\'' +
                ", nombre_generico='" + nombre_generico + '\'' +
                ", nombre_presentacion='" + nombre_presentacion + '\'' +
                ", nombre_laboratorio='" + nombre_laboratorio + '\'' +
                ", precio='" + precio + '\'' +
                ", subtotal='" + subtotal + '\'' +
                ", nombre_empaque='" + nombre_empaque + '\'' +
                ", unidades='" + unidades + '\'' +
                ", idUnique='" + idUnique + '\'' +
                '}';
    }
}
