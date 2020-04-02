package com.salud.medicalservices;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.salud.medicalservices.activitys.OtpActivity;
import com.salud.medicalservices.utils.LoadingDialogCustom;
import com.salud.medicalservices.utils.ShareDataRead;
import com.salud.medicalservices.utils.ToastCustom;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;

public class SendPhoneService extends AppCompatActivity {

    LoadingDialogCustom loadingDialogCustom;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mCurrentUser;
    private String mCompletePhoneNumber;
    private ProgressBar progressBar;

    CountryCodePicker mCountryCode;
    EditText mPhoneNumber;

    Button mGenerateCodeSms;

    TextView mTerms;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    private static final String TAG = "SendPhoneService";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendphone_service);

        mCountryCode = findViewById(R.id.ccpCountryCode);
        progressBar = findViewById(R.id.progress_cargando);
        mPhoneNumber = findViewById(R.id.etPhoneNumber);
        mGenerateCodeSms = findViewById(R.id.btnGenerateCodeSms);
        mTerms = findViewById(R.id.tvTermsAndConditions);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mCurrentUser = mFirebaseAuth.getCurrentUser();

        mTerms.setText(Html.fromHtml(getResources().getString(R.string.text_terms_and_conditions)));

        mPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enableButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mGenerateCodeSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPhoneNumber.getText().toString().isEmpty()) {

                   // Toast.makeText(SendPhoneService.this, "Verificando Usuario", Toast.LENGTH_SHORT).show();
                   // progressBar.setVisibility(View.VISIBLE);
                    String countryCode = mCountryCode.getSelectedCountryCode();
                    mCompletePhoneNumber = "+" + countryCode + mPhoneNumber.getText();

                   // progressBar.setVisibility(View.INVISIBLE);
                   // ShareDataRead.guardarValor(getApplicationContext(), "PhoneNumber", mCompletePhoneNumber);
                    loadingDialogCustom = new LoadingDialogCustom(SendPhoneService.this, "Enviando Código");
                    loadingDialogCustom.startLoadingDialog();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            mCompletePhoneNumber,
                            60,
                            TimeUnit.SECONDS,
                            SendPhoneService.this,
                            mCallbacks

                    );
                } else {
                    ToastCustom.showToast(1, SendPhoneService.this,
                            SendPhoneService.this.getLayoutInflater(),
                            "El campo teléfono es requerido.",
                            R.color.deep_orange_800);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    ToastCustom.showToast(1, SendPhoneService.this,
                            SendPhoneService.this.getLayoutInflater(),
                            "Teléfono inválido. Verifica que el código del país y el número sean correctos.",
                            R.color.deep_orange_800);
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // Toast.makeText(LoginActivity.this, "The SMS quota for the project has been exceeded", Toast.LENGTH_SHORT).show();
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                loadingDialogCustom.dismissDialog();

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                loadingDialogCustom.dismissDialog();
                Intent otpIntent = new Intent(SendPhoneService.this, ReceivePhoneActivity.class);
                otpIntent.putExtra("AuthCredentials", s);
//                otpIntent.putExtra("PhoneNumber", mCompletePhoneNumber);
                otpIntent.putExtra("PhoneNumber", mCompletePhoneNumber);
                startActivity(otpIntent);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCurrentUser != null) {
            sendUserToHome();
        }
    }

    private void enableButton() {
        boolean isReady = mPhoneNumber.getText().toString().length() > 0;

        mGenerateCodeSms.setEnabled(isReady);

        if (mGenerateCodeSms.isEnabled()) {
            mGenerateCodeSms.setTextAppearance(this, R.style.Button_Primary);
        } else {
            mGenerateCodeSms.setTextAppearance(this, R.style.Button_Disabled);
        }

        //validando Telefono
        if (!isReady) {
            //mPhoneNumber.setError("Número de teléfono incompleto");
            return;
        }
    }

    private void sendUserToHome() {
        Intent homeIntent = new Intent(SendPhoneService.this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
}
