package com.salud.medicalservices.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.salud.medicalservices.R;
import com.salud.medicalservices.adapters.RecyclerDetalleProductosAdapter;
import com.salud.medicalservices.data.ObjectDataClass;
import com.salud.medicalservices.entidades.ItemProductos;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetalleProductoActivity extends AppCompatActivity {
    RecyclerView recycler_listprod;
    private static ArrayList<ItemProductos> listProductos;

    private String productoName;
    private int img_root;

    ImageView img_toolbardetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);
        recycler_listprod = findViewById(R.id.recycler_detalleproducto);
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
            img_root =  bundle.getInt("img_root");
        }

        setTitle(productoName);
        img_toolbardetalle.setImageResource(img_root);
        listProductos = ObjectDataClass.getProductos(getApplicationContext());
        RecyclerDetalleProductosAdapter adapter = new RecyclerDetalleProductosAdapter(this, listProductos);
        recycler_listprod.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recycler_listprod.setHasFixedSize(true);
        recycler_listprod.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
