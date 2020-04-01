package com.salud.medicalservices.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.salud.medicalservices.R;
import com.salud.medicalservices.contenedores.ContentMainActivity;
import com.salud.medicalservices.entidades.AuthUser;
import com.salud.medicalservices.entidades.ItemServicios;
import com.salud.medicalservices.networking.EndPoint;
import com.salud.medicalservices.networking.MethodWs;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txv_sendpassword, txt_registrate;
    Button btnIngresar;

    RecyclerView recycler;
    RecyclerView.LayoutManager layoutManager;
    List<ItemServicios> items;
    EditText etEmail, etcontrasenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txv_sendpassword = findViewById(R.id.txr_review_password);
        txt_registrate = findViewById(R.id.txt_registrate);
        etEmail = findViewById(R.id.etEmail);
        etcontrasenha = findViewById(R.id.etcontrasenha);
        btnIngresar = findViewById(R.id.btnLogin);


        items = new ArrayList<>();

        txv_sendpassword.setOnClickListener(this);
        txt_registrate.setOnClickListener(this);
        btnIngresar.setOnClickListener(this);


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

                loginServicio();

            }
            break;

            case R.id.txt_registrate: {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

    private void loginServicio() {

        String email = etEmail.getText().toString().trim();
        String contrasenha = etcontrasenha.getText().toString().trim();
        String userRole = "User";

        AuthUser authUser = new AuthUser(email, contrasenha, userRole);
        EndPoint endPoint = MethodWs.getConfiguration().create(EndPoint.class);
        Call<ResponseBody> responseBodyCall = endPoint.postAutenticacion(authUser);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody informacion = response.body();

                if (response.isSuccessful()) {
                    try {
                        String cadena_respuesta = informacion.string();
                        Log.v("ResponseOk", cadena_respuesta);

                        Intent intent = new Intent(LoginActivity.this, ContentMainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("infoResponseLogin", informacion.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("infoError", t.getMessage());
            }
        });

//        firstName = "Resembrink";
//        lastName = "Correa";
//        email = "rcorrea@gmail.com";
//        identityDocument = "42008299";
//        address = "Santa Anita";
//        phone = "979773864";
//        birthDate = "24/09/1992";
//        genero = "Male";
//        codigoPais = "051";
//        codigoDepartamento = "015";
//        codigoProvincia = "010";
//        codigoDistrito = "010";
//        password = "9Resembrink";
//        userRole = "User";


    }
}
