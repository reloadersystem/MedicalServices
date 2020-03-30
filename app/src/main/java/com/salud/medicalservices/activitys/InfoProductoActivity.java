package com.salud.medicalservices.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.salud.medicalservices.HelperSharePreference.entity.Product;
import com.salud.medicalservices.HelperSharePreference.storage.DefaultSharedPreferencesHelper;
import com.salud.medicalservices.HelperSharePreference.utils.GsonHelper;
import com.salud.medicalservices.R;
import com.salud.medicalservices.contenedores.ContentMainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

public class InfoProductoActivity extends AppCompatActivity {

    private String nombre_comercial, nombre_generico, nombre_laboratorio, precio, nombre_presentacion, idUnique;
    private int image_producto;
    private TextView txt_nombreComercial, txt_nombregenerico, txt_nombrelaboratorio, txt_nombreprecio, txt_count;
    private ImageView img_infoProducto, img_carrito;
    private ImageButton ibtn_agregar, ibtn_menos;
    private TextView txt_countBadge;
    private ImageView image_Badge;
    private Spinner spn_empaque;

    int count = 0;
    int badgeCount = 0;

    float x1, x2, y1, y2;

    private String dataSpinner = "";
    private int contador_compra = 0;

    MenuItem menuItem;
    DefaultSharedPreferencesHelper sharedPreferencesHelper;
    private ObjectAnimator animatorXini;
    private long animationDuration = 700;

    private Double subTotal = 0.0;
    Vibrator vibrator;

    int badgeSum = 0;

    int countShare = 0;

    SharedPreferences sharPrefRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_producto);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_info);

        sharPrefRecycler = this.getSharedPreferences("RecyclerTemp", Context.MODE_PRIVATE);

        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        txt_nombreComercial = findViewById(R.id.txt_nombreComercial);
        txt_nombregenerico = findViewById(R.id.txt_nombregenerico);
        txt_nombrelaboratorio = findViewById(R.id.txt_nombrelaboratorio);
        txt_nombreprecio = findViewById(R.id.txt_nombreprecio);
        txt_count = findViewById(R.id.txt_count);
        img_infoProducto = findViewById(R.id.img_infoProducto);
        img_carrito = findViewById(R.id.img_carrito);

        ibtn_agregar = findViewById(R.id.ibtn_mas);
        ibtn_menos = findViewById(R.id.ibtn_menos);
        spn_empaque = findViewById(R.id.spn_empaque);


        setSupportActionBar(toolbar);
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
            idUnique = bundle.getString("idUnique");
        }

        txt_nombreComercial.setText(nombre_comercial);
        txt_nombregenerico.setText(nombre_generico);
        txt_nombrelaboratorio.setText(nombre_laboratorio);
        txt_nombreprecio.setText(precio);
        img_infoProducto.setImageResource(image_producto);

        ibtn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = count + 1;
                txt_count.setText(String.valueOf(count));

                int num = precio.length();
                subTotal = count * Double.valueOf(precio.substring(3, num));
            }
        });
        ibtn_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count != 0) {
                    count = count - 1;
                    txt_count.setText(String.valueOf(count));

                    int num = precio.length();
                    subTotal = count * Double.valueOf(precio.substring(3, num));
                }
            }
        });


        spn_empaque.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                dataSpinner = adapterView.getItemAtPosition(position).toString();
                // Toast.makeText(getApplicationContext(), dataSpinner, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        img_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (countShare == 0) {
                    if (count > 0) {// validación que como minio eligio mas de un producto
                        vibrator.vibrate(600);
                        movRightText();
                        movRightImage();
                        contador_compra = countShare + 1;
                        txt_countBadge.setText(String.valueOf(contador_compra));
                        String unidades = String.valueOf(contador_compra);
                        app(nombre_comercial, nombre_generico, nombre_laboratorio, nombre_presentacion, precio, subTotal.toString(), image_producto, unidades, dataSpinner, idUnique);
                    }

                } else {
                    if (count > 0) {
                        vibrator.vibrate(600);
                        movRightText();
                        movRightImage();
                        contador_compra = countShare + 1;
                        txt_countBadge.setText(String.valueOf(contador_compra));
                        String unidades = String.valueOf(count);
                        app(nombre_comercial, nombre_generico, nombre_laboratorio, nombre_presentacion, precio, subTotal.toString(), image_producto, unidades, dataSpinner, idUnique);
                    }
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_shop, menu);

        menuItem = menu.findItem(R.id.action_notifications);

        View actionview = MenuItemCompat.getActionView(menuItem);

        txt_countBadge = actionview.findViewById(R.id.notification_badge);
        image_Badge = actionview.findViewById(R.id.image_badge);

        countShare = sharPrefRecycler.getAll().size();
        txt_countBadge.setText(String.valueOf(countShare));

        actionview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_notifications: {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(InfoProductoActivity.this, ContentMainActivity.class);
                        intent.putExtra("fragmentSelected", "ServiciosFragment");
                        startActivity(intent);
                    }
                }, 1200);

                movRightImage();
                movRightText();

            }
        }
        return super.onOptionsItemSelected(item);

    }

    private void movRightText() {

        animatorXini = ObjectAnimator.ofFloat(txt_countBadge, "x", 80f);
        animatorXini.setDuration(animationDuration);
        AnimatorSet animatorSetX = new AnimatorSet();
        animatorSetX.play(animatorXini);
        animatorSetX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animation.start();
            }
        });
        animatorSetX.start();
    }

    private void movRightImage() {
        animatorXini = ObjectAnimator.ofFloat(image_Badge, "x", 60f);
        animatorXini.setDuration(animationDuration);
        AnimatorSet animatorSetX = new AnimatorSet();
        animatorSetX.play(animatorXini);
        animatorSetX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animation.start();
            }
        });
        animatorSetX.start();

    }

    private void app(String nombre_comercial, String nombre_generico, String nombre_laboratorio, String nombre_presentacion, String precio, String subTotal, int image_producto, String unidades, String nombre_empaque, String idUnique) {


        SharedPreferences sharedPreferences = this.getSharedPreferences("RecyclerTemp", Context.MODE_PRIVATE);
        String countShare = String.valueOf(sharedPreferences.getAll().size());

        GsonHelper gsonHelper = new GsonHelper();
        sharedPreferencesHelper = new DefaultSharedPreferencesHelper(gsonHelper, sharedPreferences, "recycler" + countShare);

        Product product = new Product();
        product.setNombre_comercial(nombre_comercial);
        product.setNombre_generico(nombre_generico);
        product.setNombre_laboratorio(nombre_comercial);
        product.setNombre_comercial(nombre_laboratorio);
        product.setNombre_presentacion(nombre_presentacion);
        product.setImagen_logo(image_producto);
        product.setPrecio(precio);
        product.setSubtotal(subTotal);
        product.setUnidades(unidades);
        product.setNombre_empaque(nombre_empaque);
        product.setIdUnique(idUnique);

        sharedPreferencesHelper.saveProduct(product);
        Product userSp = sharedPreferencesHelper.product();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

}


//