package com.salud.medicalservices.entidades.response;

import com.salud.medicalservices.entidades.Departamento;
import com.salud.medicalservices.entidades.Provincia;

import java.util.ArrayList;

public class ResponseProvincia {

    ArrayList<Provincia> items;

    public ArrayList<Provincia> getItems() {
        return items;
    }

    public void setItems(ArrayList<Provincia> items) {
        this.items = items;
    }
}
