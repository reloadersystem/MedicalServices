package com.salud.medicalservices.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.salud.medicalservices.R;
import com.salud.medicalservices.adapters.RecyclerAdapterSelectedServicios;
import com.salud.medicalservices.adapters.helper.RecyclerItemTouchHelper;
import com.salud.medicalservices.adapters.helper.RecyclerItemTouchHelperListener;
import com.salud.medicalservices.entidades.EntitySelectedServicios;
import com.salud.medicalservices.utils.CleanSharePref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PedidosFragment extends Fragment implements View.OnClickListener, RecyclerItemTouchHelperListener {

    View rootview;

    private List<EntitySelectedServicios> entitySelectedServicios;
    private RecyclerAdapterSelectedServicios recyclerAdapterSelectedServicios;
    private RecyclerView recycler_listSelected;
    private Button btn_realizarpedido;
    private TextView txt_costo;
    private Double countPrecio = 0.0;
    private Double swipeDescuento = 0.0;

    private CoordinatorLayout rootLayout;

    private int indicador = 0;


    public PedidosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_pedidos, container, false);


        getActivity().setTitle("Mi carrito de Compra");

        recycler_listSelected = rootview.findViewById(R.id.recycler_listSelected);

        ItemTouchHelper.SimpleCallback ItemTouchHelperCallbackLeft = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(ItemTouchHelperCallbackLeft).attachToRecyclerView(recycler_listSelected);

        btn_realizarpedido = rootview.findViewById(R.id.btn_realizarpedido);
        txt_costo = rootview.findViewById(R.id.txt_costo);


        entitySelectedServicios = new ArrayList<>();

        SharedPreferences spRecycler = getContext().getSharedPreferences("RecyclerTemp", MODE_PRIVATE);
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
                String idUnique = jsonObject.getString("idUnique");
                String subTotal = jsonObject.getString("subtotal");

                entitySelectedServicios.add(new EntitySelectedServicios(imagen_logo, nombre_comercial, nombre_generico, nombre_laboratorio, nombre_presentacion, precio, String.format("%.2f", Double.valueOf(subTotal)), nombre_empaque, unidades, idUnique));
                countPrecio = countPrecio + Double.valueOf(subTotal);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        txt_costo.setText("Total:  S/" + String.format("%.2f", countPrecio));

        GridLayoutManager layoutManager
                = new GridLayoutManager(getContext(), 1);
        recyclerAdapterSelectedServicios = new RecyclerAdapterSelectedServicios(getContext(), entitySelectedServicios);
        recycler_listSelected.setLayoutManager(layoutManager);
        recycler_listSelected.setItemAnimator(new DefaultItemAnimator());
        recycler_listSelected.setAdapter(recyclerAdapterSelectedServicios);
        btn_realizarpedido.setOnClickListener(this);

        return rootview;
    }


    public String obtenerValorRecycler(Context context, String keyPref) {
        SharedPreferences preferences = context.getSharedPreferences("RecyclerTemp", MODE_PRIVATE);
        return preferences.getString(keyPref, "");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_realizarpedido:

                CleanSharePref.deleteTemp(getContext());
                recyclerAdapterSelectedServicios.UpdateClear();
                txt_costo.setText("");
                Toast.makeText(getContext(), "Pedido realizado con éxito", Toast.LENGTH_SHORT).show();

                break;
        }


    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (indicador == 0) {
            if (viewHolder instanceof RecyclerAdapterSelectedServicios.MyViewHolder) {
                Double subtotalDescuento = Double.valueOf(entitySelectedServicios.get(viewHolder.getAdapterPosition()).getSubtotal());
                final EntitySelectedServicios deleteItem = entitySelectedServicios.get(viewHolder.getAdapterPosition());
                final int deleteIndex = viewHolder.getAdapterPosition();
                recyclerAdapterSelectedServicios.RemoveItem(deleteIndex);
                swipeDescuento = countPrecio - subtotalDescuento;
                txt_costo.setText("Total:  S/" + String.format("%.2f", (swipeDescuento)));
                indicador = indicador + 1;

                //deleteItemSharePreference(String.valueOf(deleteIndex));
            }
        } else {
            if (viewHolder instanceof RecyclerAdapterSelectedServicios.MyViewHolder) {
                Double subtotalDescuento = Double.valueOf(entitySelectedServicios.get(viewHolder.getAdapterPosition()).getSubtotal());
                final EntitySelectedServicios deleteItem = entitySelectedServicios.get(viewHolder.getAdapterPosition());
                final int deleteIndex = viewHolder.getAdapterPosition();
                recyclerAdapterSelectedServicios.RemoveItem(deleteIndex);
                swipeDescuento = swipeDescuento - subtotalDescuento;
                txt_costo.setText("Total:  S/" + String.format("%.2f", (swipeDescuento)));
            }
        }

    }

    private void deleteItemSharePreference(String KeyName) {
        SharedPreferences settings = getContext().getSharedPreferences("RecyclerTemp", Context.MODE_PRIVATE);
        settings.edit().remove(KeyName).commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        recyclerAdapterSelectedServicios.Clean();
        swipeDescuento = 0.0;
        indicador = 0;
        countPrecio = 0.0;

    }
}
