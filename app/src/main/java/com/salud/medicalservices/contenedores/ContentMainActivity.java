package com.salud.medicalservices.contenedores;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.salud.medicalservices.R;
import com.salud.medicalservices.fragments.AyudaFragment;
import com.salud.medicalservices.fragments.PedidosFragment;
import com.salud.medicalservices.fragments.PerfilFragment;
import com.salud.medicalservices.fragments.ProductosFragment;
import com.salud.medicalservices.fragments.ServiciosFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class ContentMainActivity extends AppCompatActivity {

    ProductosFragment productosFragment;
    ServiciosFragment serviciosFragment;
    PedidosFragment pedidosFragment;
    PerfilFragment perfilFragment;
    AyudaFragment ayudaFragment;
    BottomNavigationView mNavigationBottom;
    String fragmentSelected = "";

    private int mSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationBottom = findViewById(R.id.navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        productosFragment = new ProductosFragment();
        pedidosFragment = new PedidosFragment();
        serviciosFragment = new ServiciosFragment();
        perfilFragment = new PerfilFragment();
        ayudaFragment = new AyudaFragment();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            fragmentSelected = bundle.getString("fragmentSelected");
        }
        if (fragmentSelected.equalsIgnoreCase("ServiciosFragment")) {

            MenuItem ServiceItem = mNavigationBottom.getMenu().getItem(2);
            Fragment fragment = new PedidosFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            mNavigationBottom.setSelectedItemId(ServiceItem.getItemId());

        } else {
            setFragment(productosFragment);
        }


        mNavigationBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.bm_productos:
                        setFragment(productosFragment);
                        return true;

                    case R.id.bm_pedidos:
                        setFragment(pedidosFragment);
                        return true;

                    case R.id.bm_servicios:
                        setFragment(serviciosFragment);
                        return true;

                    case R.id.profileMenu:
                        setFragment(perfilFragment);
                        return true;

                    case R.id.opcion_ayuda:
                        setFragment(ayudaFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = ContentMainActivity.this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        MenuItem homeItem = mNavigationBottom.getMenu().getItem(0);

        if (mSelectedItem != homeItem.getItemId()) {

            Fragment fragment = new ProductosFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            mNavigationBottom.setSelectedItemId(homeItem.getItemId());
        } else {
            super.onBackPressed();
        }
    }
}
