package com.salud.medicalservices.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.salud.medicalservices.R;
import com.salud.medicalservices.adapters.RecyclerAdapterSubCategoria;
import com.salud.medicalservices.entidades.SubCategorias;
import com.salud.medicalservices.interfaces.OnSubCategoriasListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SubCategoriasActivity extends AppCompatActivity {

    RecyclerView recycler_listprod;
    private List<SubCategorias> mlistSubCategorias;

    private String productoName, productoId, subCategories;
    private int img_root;

    ImageView img_toolbardetalle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categorias);

        recycler_listprod = findViewById(R.id.recycler_subcategoria);
        img_toolbardetalle = findViewById(R.id.img_toolbardetalle);
        //setTitle("Reservar una Cita");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detalle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            productoName = (String) bundle.get("productoName");
            productoId = (String) bundle.get("productoId");
            subCategories = (String) bundle.get("subCategories");
            // img_root = bundle.getInt("img_root");
        }
        setTitle(productoName);

        Log.v("subData", subCategories);

        mlistSubCategorias = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(subCategories);
            for (int idx = 0; idx < jsonArray.length(); idx++) {
                JSONObject arrayItem = jsonArray.getJSONObject(idx);
                mlistSubCategorias.add(new SubCategorias(
                        arrayItem.getInt("id"), arrayItem.getString("name"),
                        arrayItem.getString("icon")));
            }

            RecyclerAdapterSubCategoria adapter = new RecyclerAdapterSubCategoria(this, mlistSubCategorias);
            recycler_listprod.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
            recycler_listprod.setHasFixedSize(true);
            recycler_listprod.setAdapter(adapter);
            adapter.setOnSubCategoriasListener(new OnSubCategoriasListener() {
                @Override
                public void onImagenClicked(int position) {

                    String subNameCategory= mlistSubCategorias.get(position).getName();
                    String productoId= String.valueOf(mlistSubCategorias.get(position).getId());


                    Intent intent = new Intent(SubCategoriasActivity.this, DetalleProductoActivity.class);
                    intent.putExtra("productoName",subNameCategory);
                    intent.putExtra("productoId",productoId);
                    startActivity(intent);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


        // img_toolbardetalle.setImageResource(img_root);

//


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
