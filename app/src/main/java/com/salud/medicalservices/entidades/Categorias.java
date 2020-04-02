package com.salud.medicalservices.entidades;

import org.json.JSONArray;

import java.util.ArrayList;

public class Categorias {

    private int id;
    private String name;
    private String icon;
    private JSONArray subCategories;

    public Categorias(int id, String name, String icon, JSONArray subCategories) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.subCategories = subCategories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public JSONArray getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(JSONArray subCategories) {
        this.subCategories = subCategories;
    }
}
