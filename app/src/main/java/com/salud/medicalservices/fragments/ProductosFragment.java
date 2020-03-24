package com.salud.medicalservices.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salud.medicalservices.R;
import com.salud.medicalservices.adapters.RecyclerProductosAdapter;
import com.salud.medicalservices.data.ObjectDataClass;
import com.salud.medicalservices.entidades.ItemServicios;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductosFragment extends Fragment {
    View rootview;

    RecyclerView recycler_productos;
    private static ArrayList<ItemServicios> listServicios;


    public ProductosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_productos, container, false);

        recycler_productos = rootview.findViewById(R.id.recycler_productos);

        listServicios = ObjectDataClass.getServicios(getContext());
        RecyclerProductosAdapter adapter = new RecyclerProductosAdapter(getContext(), listServicios);
        recycler_productos.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recycler_productos.setHasFixedSize(true);
        recycler_productos.setAdapter(adapter);
        return rootview;
    }

}
