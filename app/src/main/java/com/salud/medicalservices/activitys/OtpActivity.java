package com.salud.medicalservices.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.salud.medicalservices.R;
import com.salud.medicalservices.contenedores.ContentMainActivity;
import com.salud.medicalservices.entidades.RegisterUser;
import com.salud.medicalservices.networking.EndPoint;
import com.salud.medicalservices.networking.MethodWs;
import com.salud.medicalservices.utils.LoadingDialogCustom;
import com.salud.medicalservices.utils.ShareDataRead;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {

    LoadingDialogCustom loadingDialogCustom;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mCurrentUser;

    private String mAuthVerificationId;
    Button mBtnVerify;
    EditText mOtpText;

    String firstName, lastName, email, identityDocument, address, phone, birthDate, codigoDepartamento, codigoDistrito, codigoPais, genero, userRole, codigoProvincia;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Tools.setSystemBarColor(this);

        mBtnVerify = findViewById(R.id.btnVerify);
        mOtpText = findViewById(R.id.etOtp);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            firstName = bundle.getString("firstName");
            lastName = bundle.getString("lastName");
            email = bundle.getString("email");
            identityDocument = bundle.getString("identityDocument");
            address = bundle.getString("address");
           // phone = bundle.getString("phone");
            birthDate = bundle.getString("birthDate");
            genero = bundle.getString("genero");
            codigoPais = bundle.getString("codigoPais");
            codigoDepartamento = bundle.getString("codigoDepartamento");
            codigoProvincia = bundle.getString("codigoProvincia");
            codigoDistrito = bundle.getString("codigoDistrito");
            password = bundle.getString("password");
            userRole = bundle.getString("userRole");
        }

        loadingDialogCustom = new LoadingDialogCustom(this, "Verificando");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mCurrentUser = mFirebaseAuth.getCurrentUser();
        mAuthVerificationId = getIntent().getStringExtra("AuthCredentials");

       final String phoneNumber = getIntent().getStringExtra("phone");
        Log.v("numero", mAuthVerificationId);

        mBtnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingDialogCustom.startLoadingDialog();
                String otp = mOtpText.getText().toString().trim();

                if (otp.isEmpty()) {

                } else {

                    mBtnVerify.setEnabled(false);
                    verifyCode(otp, phoneNumber);
                    //979773864
                }
            }
        });
    }

    private void verifyCode(String otp, String phoneNumber) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mAuthVerificationId, otp);
        signInWithPhoneAuthCredential(credential, phoneNumber);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential, final String phoneNumber) {
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                          //  validarServicio();

                            Toast.makeText(OtpActivity.this, phoneNumber, Toast.LENGTH_SHORT).show();

//                            Intent registerIntent = new Intent(OtpActivity.this, ContentMainActivity.class);
//                            registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(registerIntent);
                        } else {
                            Toast.makeText(OtpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        mBtnVerify.setEnabled(true);
                        loadingDialogCustom.dismissDialog();
                    }
                });
    }

    private void validarServicio() {

//        firstName = "Resembrink";
//        lastName = "Correa";
//        email = "rcorrea@gmail.com";
//        identityDocument = "42008299";
//        address = "Santa Anita";
//        phone = "979773864/961162784";
//        birthDate = "24/09/1992";
//        genero = "Male";
//        codigoPais = "051";
//        codigoDepartamento = "015";
//        codigoProvincia = "010";
//        codigoDistrito = "010";
//        password = "9Resembrink";
//        userRole = "User";

        RegisterUser registerUser = new RegisterUser(firstName, lastName, email, identityDocument, address, phone, birthDate, genero, codigoPais, codigoDepartamento, codigoProvincia, codigoDistrito, password, userRole);
        EndPoint endPoint = MethodWs.getConfiguration().create(EndPoint.class);
        Call<ResponseBody> responseBodyCall = endPoint.postRegistrarUsuario(registerUser);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody informacion = response.body();

                if (response.isSuccessful()) {
                    try {
                        String cadena_respuesta = informacion.string();
                        Log.v("RsptaResponsePost", cadena_respuesta);

                        ShareDataRead.guardarValor(getApplicationContext(), "firstName", firstName);
                        ShareDataRead.guardarValor(getApplicationContext(), "lastName", lastName);
                        ShareDataRead.guardarValor(getApplicationContext(), "email", email);
                        ShareDataRead.guardarValor(getApplicationContext(), "identityDocument", identityDocument);
                        ShareDataRead.guardarValor(getApplicationContext(), "address", address);
                        ShareDataRead.guardarValor(getApplicationContext(), "phone", phone);
                        ShareDataRead.guardarValor(getApplicationContext(), "birthDate", birthDate);
                        ShareDataRead.guardarValor(getApplicationContext(), "genero", genero);
                        ShareDataRead.guardarValor(getApplicationContext(), "codigoPais", codigoPais);
                        ShareDataRead.guardarValor(getApplicationContext(), "codigoDepartamento", codigoDepartamento);
                        ShareDataRead.guardarValor(getApplicationContext(), "codigoProvincia", codigoProvincia);
                        ShareDataRead.guardarValor(getApplicationContext(), "codigoDistrito", codigoDistrito);
                        ShareDataRead.guardarValor(getApplicationContext(), "password", password);
                        ShareDataRead.guardarValor(getApplicationContext(), "userRole", userRole);

                        Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("infoResponsePost", informacion.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("infoResponseErrorPost", t.getMessage());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCurrentUser != null) {
            sendUserToHome();
        }
    }

    private void sendUserToHome() {
        Intent homeIntent = new Intent(OtpActivity.this, ContentMainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
