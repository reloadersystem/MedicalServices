package com.salud.medicalservices.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.salud.medicalservices.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

public class InfoProductoActivity extends AppCompatActivity {

    private String nombre_comercial, nombre_generico, nombre_laboratorio, precio, nombre_presentacion;
    private int image_producto;

    TextView txt_nombreComercial, txt_nombregenerico, txt_nombrelaboratorio, txt_nombreprecio;

    ImageView img_infoProducto;

    TextView txt_countBadge;

    int pendinCount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_producto);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_info);

        txt_nombreComercial = findViewById(R.id.txt_nombreComercial);
        txt_nombregenerico = findViewById(R.id.txt_nombregenerico);
        txt_nombrelaboratorio = findViewById(R.id.txt_nombrelaboratorio);
        txt_nombreprecio = findViewById(R.id.txt_nombreprecio);
        img_infoProducto = findViewById(R.id.img_infoProducto);

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
            image_producto = bundle.getInt("image_producto");
        }

        txt_nombreComercial.setText(nombre_comercial);
        txt_nombregenerico.setText(nombre_generico);
        txt_nombrelaboratorio.setText(nombre_laboratorio);
        txt_nombreprecio.setText(precio);
        img_infoProducto.setImageResource(image_producto);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shop, menu);

        final MenuItem menuItem = menu.findItem(R.id.menu_itemshop);

        View actionview = MenuItemCompat.getActionView(menuItem);

        // TextView txt_countBadge = actionview.findViewById(R.id.txt_countBadge);

        //setupBadge();

//        actionview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onOptionsItemSelected(menuItem);
//            }
//        });

        return true;
    }

    private void setupBadge() {
        if (txt_countBadge != null) {
            if (pendinCount == 0) {
                if (txt_countBadge.getVisibility() != View.GONE) {
                    txt_countBadge.setVisibility(View.GONE);
                } else {
                    txt_countBadge.setText("86");
                    if (txt_countBadge.getVisibility() != View.VISIBLE) {
                        txt_countBadge.setVisibility(View.VISIBLE);
                    }
                }

            }
        }
    }
}
