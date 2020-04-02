package com.salud.medicalservices.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.salud.medicalservices.R;
import com.salud.medicalservices.contenedores.ContentMainActivity;
import com.salud.medicalservices.entidades.AuthUser;
import com.salud.medicalservices.entidades.ItemServicios;
import com.salud.medicalservices.entidades.Loguin;
import com.salud.medicalservices.entidades.Usuario;
import com.salud.medicalservices.networking.EndPoint;
import com.salud.medicalservices.networking.MethodWs;
import com.salud.medicalservices.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;
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

    SweetAlertDialog pd;


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

        pd = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pd.getProgressHelper().setBarColor(Color.parseColor("#102670"));
        pd.setContentText("Por favor, espere...");
        pd.setCancelable(false);
        pd.show();

        String email = etEmail.getText().toString().trim();
        String contrasenha = etcontrasenha.getText().toString().trim();
        String userRole = "User";

        AuthUser authUser = new AuthUser(email, contrasenha, userRole);
        EndPoint endPoint = MethodWs.getConfiguration().create(EndPoint.class);
        Call<Loguin> responseBodyCall = endPoint.postAutenticacion(authUser);
        responseBodyCall.enqueue(new Callback<Loguin>() {

            @Override
            public void onResponse(Call<Loguin> call, Response<Loguin> response) {

                if (response.isSuccessful()) {
                    try {

                        Loguin user_response = response.body();

                        if (response.code() == Constantes.SUCCESS) {
                            guardarPreferenciaPerfil(Constantes.ROL_USER, user_response.getToken());
                            obtenerDatosUsuario(user_response.getToken());
                        } else if (response.code() == Constantes.BAD_REQUEST) {

                            pd.dismiss();

                            pd = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE);
                            pd.getProgressHelper().setBarColor(Color.parseColor("#03A9F4"));
                            pd.setContentText(user_response.getDetail());
                            pd.setCancelable(false);
                            pd.show();
                            return;
                        } else {

                            pd.dismiss();

                            pd = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE);
                            pd.getProgressHelper().setBarColor(Color.parseColor("#03A9F4"));
                            pd.setContentText("Hubo un problema al procesar su solicitud");
                            pd.setCancelable(false);
                            pd.show();
                            return;

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    pd.dismiss();

                    pd = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE);
                    pd.getProgressHelper().setBarColor(Color.parseColor("#03A9F4"));
                    pd.setContentText("Hubo un problema al procesar su solicitud");
                    pd.setCancelable(false);
                    pd.show();
                    return;

                }
            }

            @Override
            public void onFailure(Call<Loguin> call, Throwable t) {

                pd.dismiss();

                pd = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE);
                pd.getProgressHelper().setBarColor(Color.parseColor("#03A9F4"));
                pd.setContentText(t.getMessage().toString());
                pd.setCancelable(false);
                pd.show();
                return;
            }
        });
    }

    private void obtenerDatosUsuario(String token) {

        EndPoint endPoint = MethodWs.getConfiguration().create(EndPoint.class);

        //Llamamos al endpoint
        Call<Usuario> response =
                endPoint.obtenerInformacionUsuario(Constantes.AUTH + token);

        response.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if (response.isSuccessful()) {

                    Usuario usuario = response.body();

                    if (response.code() == Constantes.SUCCESS) {

                        guardarPreferenciaUsuario(usuario);
                        pd.dismiss();

                        Intent i = new Intent(LoginActivity.this, ContentMainActivity.class);
                        startActivity(i);
                        finish();

                    } else {

                        pd.dismiss();

                        pd = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE);
                        pd.getProgressHelper().setBarColor(Color.parseColor("#03A9F4"));
                        pd.setContentText(usuario.getDetail());
                        pd.setCancelable(false);
                        pd.show();
                        return;
                    }
                } else {

                    pd.dismiss();

                    pd = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE);
                    pd.getProgressHelper().setBarColor(Color.parseColor("#03A9F4"));
                    pd.setContentText("Hubo un problema al procesar su solicitud");
                    pd.setCancelable(false);
                    pd.show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

                pd.dismiss();

                pd = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE);
                pd.getProgressHelper().setBarColor(Color.parseColor("#03A9F4"));
                pd.setContentText(t.getMessage());
                pd.setCancelable(false);
                pd.show();
                return;
            }
        });
    }

    private void guardarPreferenciaPerfil(String perfil, String token) {
        SharedPreferences.Editor editor = getSharedPreferences("perfil", 0).edit();
        editor.putString("perfil", perfil);
        editor.putString("token", token);
        editor.commit();
    }

    private void guardarPreferenciaUsuario(Usuario usuario) {
        SharedPreferences.Editor editor = getSharedPreferences("usuario", 0).edit();
        editor.putString("id", usuario.getId());
        editor.putString("firstName", usuario.getFirstName());
        editor.putString("lastName", usuario.getLastName());
        editor.putString("email", usuario.getEmail());
        editor.putString("address", usuario.getAddress());
        editor.putString("countryId", usuario.getCountryId());
        editor.putString("departmentId", usuario.getDepartmentId());
        editor.putString("provinceId", usuario.getProvinceId());
        editor.putString("districtId", usuario.getDistrictId());
        editor.putString("gender", usuario.getGender());
        editor.putString("phone", usuario.getPhone());
        editor.putString("identityDocument", usuario.getIdentityDocument());
        editor.putString("birthDate", usuario.getBirthDate());
        editor.commit();


    }
}
