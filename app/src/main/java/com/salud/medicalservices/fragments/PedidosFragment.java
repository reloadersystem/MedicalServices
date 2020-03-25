package com.salud.medicalservices.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salud.medicalservices.R;
import com.salud.medicalservices.adapters.RecyclerAdapterSelectedServicios;
import com.salud.medicalservices.entidades.EntitySelectedServicios;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PedidosFragment extends Fragment {

    View rootview;

    List<EntitySelectedServicios> entitySelectedServicios;
    RecyclerAdapterSelectedServicios recyclerAdapterSelectedServicios;
    RecyclerView recycler_listSelected;


    public PedidosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_pedidos, container, false);

        recycler_listSelected = rootview.findViewById(R.id.recycler_listSelected);

        entitySelectedServicios = new ArrayList<>();

        mostrarRecyclerServiciosSeleccionados();

        return rootview;
    }

    private void mostrarRecyclerServiciosSeleccionados() {

        SharedPreferences spRecycler = getContext().getSharedPreferences("RecyclerTemp", Context.MODE_PRIVATE);
        int countTemp = spRecycler.getAll().size();

        for (int a = 0; a < countTemp; a++) {

            String saveRecycler = obtenerValorRecycler(getContext(), "recycler" + a);
            try {
                JSONObject jsonObject = new JSONObject(saveRecycler);
                int imagen_logo = jsonObject.getInt("imagen_logo");
                String nombre_comercial = jsonObject.getString("nombre_comercial");
                String nombre_generico = jsonObject.getString("nombre_generico");
                String nombre_laboratorio = jsonObject.getString("nombre_laboratorio");
                String nombre_presentacion = jsonObject.getString("nombre_presentacion");
                String nombre_empaque = jsonObject.getString("nombre_empaque");
                String precio = jsonObject.getString("precio");
                String unidades = jsonObject.getString("unidades");

                entitySelectedServicios.add(new EntitySelectedServicios(imagen_logo, nombre_comercial, nombre_generico, nombre_laboratorio, nombre_presentacion, precio, nombre_empaque, unidades));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        GridLayoutManager layoutManager
                = new GridLayoutManager(getContext(), 1);
        recyclerAdapterSelectedServicios = new RecyclerAdapterSelectedServicios(getContext(), entitySelectedServicios);
        recyclerAdapterSelectedServicios.Update();
        recycler_listSelected.setLayoutManager(layoutManager);
        recycler_listSelected.setAdapter(recyclerAdapterSelectedServicios);


    }


    public String obtenerValorRecycler(Context context, String keyPref) {
        SharedPreferences preferences = context.getSharedPreferences("RecyclerTemp", MODE_PRIVATE);
        return preferences.getString(keyPref, "");
    }

}
