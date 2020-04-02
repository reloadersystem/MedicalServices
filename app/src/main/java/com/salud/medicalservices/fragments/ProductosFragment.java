package com.salud.medicalservices.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salud.medicalservices.R;
import com.salud.medicalservices.activitys.SubCategoriasActivity;
import com.salud.medicalservices.adapters.RecyclerProductosAdapter;
import com.salud.medicalservices.data.ObjectDataClass;
import com.salud.medicalservices.entidades.Categorias;
import com.salud.medicalservices.entidades.ItemServicios;
import com.salud.medicalservices.interfaces.OnProductosListener;
import com.salud.medicalservices.networking.EndPoint;
import com.salud.medicalservices.networking.MethodWs;
import com.salud.medicalservices.utils.Constantes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductosFragment extends Fragment {
    View rootview;

    RecyclerView recycler_productos;
    private static ArrayList<ItemServicios> listServicios;
    private List<Categorias> mlistCategorias;


    public ProductosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_productos, container, false);

        recycler_productos = rootview.findViewById(R.id.recycler_productos);

        obtenerCategorias("Product");

        listServicios = ObjectDataClass.getServicios(getContext());

        return rootview;
    }

    private void obtenerCategorias(String Type) {

        String token = obtenerSharePerfil(getContext(), "token");
        EndPoint endPoint = MethodWs.getConfiguration().create(EndPoint.class);
        Call<ResponseBody> responseBodyCall = endPoint.getCategorias(Constantes.AUTH + token, Type);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody informacion = response.body();
                mlistCategorias = new ArrayList<>();
                if (response.isSuccessful()) {
                    try {
                        String cadena_respuesta = informacion.string();
                        Log.v("RsptaResponse", cadena_respuesta);

                        JSONObject jsonObject = new JSONObject(cadena_respuesta);

                        JSONArray resArraydata = new JSONArray(jsonObject.getString("items"));

                        for (int idx = 0; idx < resArraydata.length(); idx++) {
                            JSONObject arrayItem = resArraydata.getJSONObject(idx);
                            mlistCategorias.add(new Categorias(arrayItem.getInt("id"), arrayItem.getString("name"), arrayItem.getString("icon"), arrayItem.getJSONArray("subCategories")));
                        }

                        RecyclerProductosAdapter adapter = new RecyclerProductosAdapter(getContext(), mlistCategorias);
                        recycler_productos.setLayoutManager(new GridLayoutManager(getContext(), 1));
                        recycler_productos.setHasFixedSize(true);
                        recycler_productos.setAdapter(adapter);
                        adapter.setOnProductosListener(new OnProductosListener() {
                            @Override
                            public void onImagenClicked(int position) {

                                String productoName = mlistCategorias.get(position).getName();
                                String productoId = String.valueOf(mlistCategorias.get(position).getId());
                                String subCategories = String.valueOf(mlistCategorias.get(position).getSubCategories());

                                //int img_root = mlistCategorias.get(position).getIcon();

//                                Intent intent = new Intent(getContext(), DetalleProductoActivity.class);
                                Intent intent = new Intent(getContext(), SubCategoriasActivity.class);
                                intent.putExtra("productoName", productoName);
                                intent.putExtra("productoId", productoId);
                                intent.putExtra("subCategories", subCategories);
                                // intent.putExtra("img_root", img_root);
                                getContext().startActivity(intent);
                                //((Activity) getContext()).finish();

                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("infoResponseFalse", t.getMessage());
            }
        });
    }

    static String PREFS_KEY = "perfil";

    public static String obtenerSharePerfil(Context context, String keyPref) {

        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        return preferences.getString(keyPref, "");
    }

}
