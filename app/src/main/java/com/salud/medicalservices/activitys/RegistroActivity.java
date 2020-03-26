package com.salud.medicalservices.activitys;

import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.salud.medicalservices.R;

public class RegistroActivity extends AppCompatActivity {

    Button btn_registro;

    SweetAlertDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        setTitle("Registro");

        btn_registro = findViewById(R.id.btn_registro);

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd = new SweetAlertDialog(RegistroActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                pd.setTitleText("Informativo");
                pd.setContentText("Registrado correctamente!!");
                pd.setConfirmText("Continuar");
                pd.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        Intent i = new Intent(RegistroActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }).show();

            }
        });



    }
}
