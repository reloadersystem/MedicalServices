package com.salud.medicalservices.entidades.response;

import com.salud.medicalservices.entidades.Distrito;
import com.salud.medicalservices.entidades.Pais;

import java.util.ArrayList;

public class ResponseDistrito {

    ArrayList<Distrito> items;

    public ArrayList<Distrito> getItems() {
        return items;
    }

    public void setItems(ArrayList<Distrito> items) {
        this.items = items;
    }
}
