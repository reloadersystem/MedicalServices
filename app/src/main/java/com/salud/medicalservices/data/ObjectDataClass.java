package com.salud.medicalservices.data;

import android.content.Context;

import com.salud.medicalservices.R;
import com.salud.medicalservices.entidades.ItemServicios;

import java.util.ArrayList;

public class ObjectDataClass {

    public static ArrayList<ItemServicios> getServicios(Context context) {

        ArrayList<ItemServicios> mListServicios = new ArrayList<>();
       
        mListServicios.add(new ItemServicios(R.drawable.service_1, "Medicamentos"));
        mListServicios.add(new ItemServicios(R.drawable.service_2, "Cuidado y aseo Personal"));
        mListServicios.add(new ItemServicios(R.drawable.service_3, "Belleza"));
        mListServicios.add(new ItemServicios(R.drawable.service_4, "Bebé"));
        mListServicios.add(new ItemServicios(R.drawable.service_5, "Cuidado personal y Salud"));
        mListServicios.add(new ItemServicios(R.drawable.service_6, "Cuidado de manos y pies"));
        mListServicios.add(new ItemServicios(R.drawable.service_7, "Nutrición y Dieta"));
        return mListServicios;
    }
}
