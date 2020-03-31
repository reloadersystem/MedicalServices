package com.salud.medicalservices.activitys;

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
import com.salud.medicalservices.R;
import com.salud.medicalservices.contenedores.ContentMainActivity;
import com.salud.medicalservices.utils.LoadingDialogCustom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class OtpActivity extends AppCompatActivity {

    LoadingDialogCustom loadingDialogCustom;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mCurrentUser;

    private String mAuthVerificationId;
    Button mBtnVerify;
    EditText mOtpText;

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
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            if (phoneNumber.equalsIgnoreCase(phoneNumber)) {
                                sendUserToHome();
                            } else {
                                Intent registerIntent = new Intent(OtpActivity.this, RegisterActivity.class);
                                startActivity(registerIntent);
                            }
                        } else {
                            Toast.makeText(OtpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        mBtnVerify.setEnabled(true);
                        loadingDialogCustom.dismissDialog();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (mCurrentUser != null) {
//            sendUserToHome();
//        }
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
