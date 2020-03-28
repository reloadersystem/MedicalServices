package com.salud.medicalservices.data;

import android.content.Context;

import com.salud.medicalservices.R;
import com.salud.medicalservices.entidades.ItemProductos;
import com.salud.medicalservices.entidades.ItemServicios;

import java.util.ArrayList;

public class ObjectDataClass {

    public static ArrayList<ItemServicios> getServicios(Context context) {

        ArrayList<ItemServicios> mListServicios = new ArrayList<>();

        mListServicios.add(new ItemServicios(R.drawable.service_1, "Medicamentos"));
        mListServicios.add(new ItemServicios(R.drawable.service_2, "Cuidado y aseo Personal"));
        mListServicios.add(new ItemServicios(R.drawable.service_3, "Belleza"));
        mListServicios.add(new ItemServicios(R.drawable.service_4, "Adulto Mayor"));
        mListServicios.add(new ItemServicios(R.drawable.service_5, "Cuidado personal y Salud"));
        mListServicios.add(new ItemServicios(R.drawable.service_6, "Cuidado de manos y pies"));
        mListServicios.add(new ItemServicios(R.drawable.service_7, "Nutrición y Dieta"));
        return mListServicios;
    }

    public static ArrayList<ItemProductos> getServicios2(Context context) {

        ArrayList<ItemProductos> mListServicios = new ArrayList<>();

        mListServicios.add(new ItemProductos(R.drawable.farmaprod_1, "A106","Servicio de toma de Sangre", "", "", "", "S/. 15.00"));
        mListServicios.add(new ItemProductos(R.drawable.farmaprod_1, "A107","Servicio de toma de Orina", "", "", "", "S/. 40.00"));
        mListServicios.add(new ItemProductos(R.drawable.farmaprod_1, "A108","Servicio de prueba XYZ de ", "", "", "", "S/. 115.00"));


        return mListServicios;
    }

    public static ArrayList<ItemProductos> getProductos(Context context) {

        ArrayList<ItemProductos> mListProductos = new ArrayList<>();

        mListProductos.add(new ItemProductos(R.drawable.farmaprod_1, "A100","Vitagel", "Collagen Hydrolysate", "Frasco", "SWITZERLAND", "S/. 15.00"));
        mListProductos.add(new ItemProductos(R.drawable.farmaprod_2, "A101","NatC", "Yummy Gummyz", "Caja", "MEGA", "S/. 40.00"));
        mListProductos.add(new ItemProductos(R.drawable.farmaprod_3, "A102","Enfagrow", "Pro Mental", "Frasco 850g", "DHA", "S/. 115.00"));
        mListProductos.add(new ItemProductos(R.drawable.farmaprod_4, "A103","Ensure", "Ensure Advance", "Caja", "Laboratorio Portugal", "S/. 55.50"));
        mListProductos.add(new ItemProductos(R.drawable.farmaprod_5, "A104","NAN", "NAN Crecimiento", "Frasco 800g", "Nestle", "S/. 78.10"));
        mListProductos.add(new ItemProductos(R.drawable.farmaprod_6, "A105","GERIA PLUS", "Geria Plus For Life", "Frasco", "Welliimune", "S/. 40.00"));
        return mListProductos;
    }
}
