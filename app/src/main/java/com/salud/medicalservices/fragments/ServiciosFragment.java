package com.salud.medicalservices.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salud.medicalservices.R;
import com.salud.medicalservices.adapters.RecyclerServicioAdapter;
import com.salud.medicalservices.data.ObjectDataClass;
import com.salud.medicalservices.entidades.ItemProductos;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiciosFragment extends Fragment {

    RecyclerView recycler_servicio;
    //PedidoDetalleAdapter adapter;

    private static ArrayList<ItemProductos> listProductos;

    public ServiciosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_servicios, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        getActivity().setTitle("Servicios");
        recycler_servicio = getActivity().findViewById(R.id.recycler_servicio);
        //img_toolbardetalle = findViewById(R.id.img_toolbardetalle);
        //setTitle("Reservar una Cita");
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detalle);
        //setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayShowTitleEnabled(true);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listProductos = ObjectDataClass.getServicios2(getActivity().getApplicationContext());
        RecyclerServicioAdapter adapter = new RecyclerServicioAdapter(getContext(), listProductos);
        recycler_servicio.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recycler_servicio.setHasFixedSize(true);
        recycler_servicio.setAdapter(adapter);
    }
}
