package com.salud.medicalservices.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.salud.medicalservices.R;
import com.salud.medicalservices.contenedores.ContentMainActivity;
import com.salud.medicalservices.entidades.ItemServicios;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txv_sendpassword;
    Button btnIngresar,btn_registrarme;

    RecyclerView recycler;
    RecyclerView.LayoutManager layoutManager;
    List<ItemServicios> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txv_sendpassword = findViewById(R.id.txr_review_password);
        btnIngresar = findViewById(R.id.btnLogin);
        btn_registrarme = findViewById(R.id.btn_registrarme);

        items = new ArrayList<>();

        txv_sendpassword.setOnClickListener(this);
        btnIngresar.setOnClickListener(this);
        btn_registrarme.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.txr_review_password: {
                Intent intent = new Intent(LoginActivity.this, SendPasswordActivity.class);
                startActivity(intent);
            }
            break;

            case R.id.btnLogin: {
                Intent intent = new Intent(LoginActivity.this, ContentMainActivity.class);
                startActivity(intent);
                finish();
            }
            break;
            case R.id.btn_registrarme: {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
                finish();
            }
            break;
        }
    }
}
