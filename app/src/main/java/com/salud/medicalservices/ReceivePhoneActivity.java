package com.salud.medicalservices;

import android.content.Intent;
import android.os.Bundle;
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
import com.salud.medicalservices.utils.LoadingDialogCustom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ReceivePhoneActivity extends AppCompatActivity {

    private static final String TAG = "ReceiveActivity";

    LoadingDialogCustom loadingDialogCustom;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mCurrentUser;

    private String mAuthVerificationId;

    Button mBtnVerify;
    EditText mOtpText;

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
        final String phoneNumber = getIntent().getStringExtra("PhoneNumber");

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
                            //Verificar si el usuario existe o no en base al numero de telefono enviado en base de datos.
//                        if(phoneNumber.equals("+51961162784")){//si existe dejar pasar
//                            if (phoneNumber.equals("+51981179706")) {//si existe dejar pasar
//
//                                sendUserToHome();
//                            } else {
                            //mostrar formulario de registro
                            Toast.makeText(getApplicationContext(), phoneNumber, Toast.LENGTH_LONG).show();
//                            Intent registerIntent = new Intent(ReceivePhoneActivity.this, RegisterActivity.class);
//                            startActivity(registerIntent);

//                            }

                        } else {
                            // Log.e(TAG, "onComplete: " + task.getException().getMessage());
                            Toast.makeText(ReceivePhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        mBtnVerify.setEnabled(true);
                        loadingDialogCustom.dismissDialog();
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
        Intent homeIntent = new Intent(ReceivePhoneActivity.this, MainActivity.class);
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
