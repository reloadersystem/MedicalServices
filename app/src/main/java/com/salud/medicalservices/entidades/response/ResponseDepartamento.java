package com.salud.medicalservices.entidades.response;

import com.salud.medicalservices.entidades.Departamento;
import com.salud.medicalservices.entidades.Pais;

import java.util.ArrayList;

public class ResponseDepartamento {

    ArrayList<Departamento> items;

    public ArrayList<Departamento> getItems() {
        return items;
    }

    public void setItems(ArrayList<Departamento> items) {
        this.items = items;
    }
}
