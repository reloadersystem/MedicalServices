package com.salud.medicalservices;

import android.content.Intent;
import android.graphics.Color;
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
import com.salud.medicalservices.activitys.LoginActivity;
import com.salud.medicalservices.activitys.OtpActivity;
import com.salud.medicalservices.entidades.APIError;
import com.salud.medicalservices.entidades.RegisterUser;
import com.salud.medicalservices.networking.EndPoint;
import com.salud.medicalservices.networking.MethodWs;
import com.salud.medicalservices.utils.ErrorUtils;
import com.salud.medicalservices.utils.LoadingDialogCustom;
import com.salud.medicalservices.utils.ShareDataRead;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceivePhoneActivity extends AppCompatActivity {

    private static final String TAG = "ReceiveActivity";

    LoadingDialogCustom loadingDialogCustom;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mCurrentUser;

    private String mAuthVerificationId;

    Button mBtnVerify;
    EditText mOtpText;

    SweetAlertDialog pd;

    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_phone);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Tools.setSystemBarColor(this);

        mBtnVerify = findViewById(R.id.btnVerify);
        mOtpText = findViewById(R.id.etOtp);

        loadingDialogCustom = new LoadingDialogCustom(this, "Verificando");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mCurrentUser = mFirebaseAuth.getCurrentUser();
        mAuthVerificationId = getIntent().getStringExtra("AuthCredentials");
        phoneNumber = getIntent().getStringExtra("PhoneNumber");

        mBtnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingDialogCustom.startLoadingDialog();
                String otp = mOtpText.getText().toString().trim();

                if (otp.isEmpty()) {

                } else {

                    mBtnVerify.setEnabled(false);
                    verifyCode(otp, phoneNumber);
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
                //.addOnCompleteListener(OtpActivity.this, new ;
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            //Toast.makeText(getApplicationContext(), phoneNumber, Toast.LENGTH_LONG).show();
                            //TODO  NUMERO EXITOSO FALTA VALIDAR SERVICIO
                            loadingDialogCustom.dismissDialog();
                            validarServicio();


                        } else {
                            // Log.e(TAG, "onComplete: " + task.getException().getMessage());
                            //Toast.makeText(ReceivePhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            loadingDialogCustom.dismissDialog();

                            pd = new SweetAlertDialog(ReceivePhoneActivity.this, SweetAlertDialog.WARNING_TYPE);
                            pd.getProgressHelper().setBarColor(Color.parseColor("#03A9F4"));
                            pd.setContentText(task.getException().getMessage());
                            pd.setCancelable(false);
                            pd.show();
                            return;
                        }
                        mBtnVerify.setEnabled(true);
                        loadingDialogCustom.dismissDialog();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

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

    private void validarServicio() {



        pd = new SweetAlertDialog(ReceivePhoneActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pd.getProgressHelper().setBarColor(Color.parseColor("#102670"));
        pd.setContentText("Procesando su solicitud...");
        pd.setCancelable(false);
        pd.show();

        String firstName = ShareDataRead.obtenerValor(getApplicationContext(), "firstName");
        String lastName = ShareDataRead.obtenerValor(getApplicationContext(), "lastName");
        String email = ShareDataRead.obtenerValor(getApplicationContext(), "email");
        String identityDocument = ShareDataRead.obtenerValor(getApplicationContext(), "identityDocument");
        String address = ShareDataRead.obtenerValor(getApplicationContext(), "address");
        String password = ShareDataRead.obtenerValor(getApplicationContext(), "password");
        String birthDate = ShareDataRead.obtenerValor(getApplicationContext(), "birthDate");
        String codigoDepartamento = ShareDataRead.obtenerValor(getApplicationContext(), "codigoDepartamento");
        String codigoDistrito = ShareDataRead.obtenerValor(getApplicationContext(), "codigoDistrito");
        String codigoProvincia = ShareDataRead.obtenerValor(getApplicationContext(), "codigoProvincia");
        String codigoPais = ShareDataRead.obtenerValor(getApplicationContext(), "codigoPais");
        String genero = ShareDataRead.obtenerValor(getApplicationContext(), "genero");
        String userRole = ShareDataRead.obtenerValor(getApplicationContext(), "userRole");


        RegisterUser registerUser = new RegisterUser(firstName, lastName, email, identityDocument, address, phoneNumber, birthDate, genero, codigoPais, codigoDepartamento, codigoProvincia, codigoDistrito, password, userRole);
        EndPoint endPoint = MethodWs.getConfiguration().create(EndPoint.class);
        Call<ResponseBody> responseBodyCall = endPoint.postRegistrarUsuario(registerUser);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                if (response.isSuccessful()) {
                    try {
                        ResponseBody informacion = response.body();

                        String cadena_respuesta = informacion.string();
                        Log.v("RsptaResponsePost", cadena_respuesta);

                        pd.dismiss();

                        new SweetAlertDialog(ReceivePhoneActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Informativo")
                                .setContentText("Celular validado correctamente!!")
                                .setConfirmText("Continuar")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {


                                        Intent intent = new Intent(ReceivePhoneActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).show();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else{

                    APIError error = ErrorUtils.parseError(response);

                    pd.dismiss();

                    pd = new SweetAlertDialog(ReceivePhoneActivity.this, SweetAlertDialog.WARNING_TYPE);
                    pd.getProgressHelper().setBarColor(Color.parseColor("#03A9F4"));
                    pd.setContentText(error.getDetail());
                    pd.setCancelable(false);
                    pd.show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pd = new SweetAlertDialog(ReceivePhoneActivity.this, SweetAlertDialog.WARNING_TYPE);
                pd.getProgressHelper().setBarColor(Color.parseColor("#03A9F4"));
                pd.setContentText(t.getMessage().toString());
                pd.setCancelable(false);
                pd.show();
                return;
            }
        });
    }

}
