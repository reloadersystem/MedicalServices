package com.salud.medicalservices.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.salud.medicalservices.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class InfoProductoActivity extends AppCompatActivity {

    private String nombre_comercial, nombre_generico, nombre_laboratorio, precio, nombre_presentacion;

    TextView txt_nombreComercial, txt_nombregenerico, txt_nombrelaboratorio, txt_nombreprecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_producto);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_info);

        txt_nombreComercial= findViewById(R.id.txt_nombreComercial);
        txt_nombregenerico= findViewById(R.id.txt_nombregenerico);
        txt_nombrelaboratorio= findViewById(R.id.txt_nombrelaboratorio);
        txt_nombreprecio= findViewById(R.id.txt_nombreprecio);

        setSupportActionBar(toolbar);
        setTitle("Información del Producto");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            nombre_comercial = (String) bundle.get("nombre_comercial");
            nombre_generico = bundle.getString("nombre_generico");
            nombre_laboratorio = bundle.getString("nombre_laboratorio");
            nombre_presentacion = bundle.getString("nombre_presentacion");
            precio = bundle.getString("precio");
        }

        txt_nombreComercial.setText(nombre_comercial);
        txt_nombregenerico.setText(nombre_generico);
        txt_nombrelaboratorio.setText(nombre_laboratorio);
        txt_nombreprecio.setText(precio);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
