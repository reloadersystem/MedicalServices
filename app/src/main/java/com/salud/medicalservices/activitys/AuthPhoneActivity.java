package com.salud.medicalservices.activitys;

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
import com.salud.medicalservices.R;
import com.salud.medicalservices.contenedores.ContentMainActivity;
import com.salud.medicalservices.utils.LoadingDialogCustom;
import com.salud.medicalservices.utils.ShareDataRead;
import com.salud.medicalservices.utils.ToastCustom;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;

public class AuthPhoneActivity extends AppCompatActivity {

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

    String firstName, lastName, email, identityDocument, address, phone, birthDate, codigoDepartamento, codigoDistrito, codigoPais, genero, userRole, codigoProvincia;
    String password;
    private static final String TAG = "AuthPhoneActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_phone);

        mCountryCode = findViewById(R.id.ccpCountryCode);
        progressBar = findViewById(R.id.progress_cargando);
        mPhoneNumber = findViewById(R.id.etPhoneNumber);
        mGenerateCodeSms = findViewById(R.id.btnGenerateCodeSms);
        mTerms = findViewById(R.id.tvTermsAndConditions);


        progressBar = findViewById(R.id.progress_cargando);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mCurrentUser = mFirebaseAuth.getCurrentUser();


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            firstName = bundle.getString("firstName");
            lastName = bundle.getString("lastName");
            email = bundle.getString("email");
            identityDocument = bundle.getString("identityDocument");
            address = bundle.getString("address");
            phone = bundle.getString("phone");
            birthDate = bundle.getString("birthDate");
            genero = bundle.getString("genero");
            codigoPais = bundle.getString("codigoPais");
            codigoDepartamento = bundle.getString("codigoDepartamento");
            codigoProvincia = bundle.getString("codigoProvincia");
            codigoDistrito = bundle.getString("codigoDistrito");
            password = bundle.getString("password");
            userRole = bundle.getString("userRole");
        }

        mTerms.setText(Html.fromHtml(getResources().getString(R.string.text_terms_and_conditions)));

        mPhoneNumber.setText(phone);
        enableButton();

        mPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mGenerateCodeSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mPhoneNumber.getText().toString().isEmpty()) {

                    Toast.makeText(AuthPhoneActivity.this, "Verificando Usuario", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.VISIBLE);
                    String countryCode = mCountryCode.getSelectedCountryCode();

                    mCompletePhoneNumber = "+" + countryCode + mPhoneNumber.getText();

                    progressBar.setVisibility(View.INVISIBLE);
                    ShareDataRead.guardarValor(getApplicationContext(), "PhoneNumber", mCompletePhoneNumber);
                    loadingDialogCustom = new LoadingDialogCustom(AuthPhoneActivity.this, "Enviando Código");
                    loadingDialogCustom.startLoadingDialog();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            mCompletePhoneNumber,
                            60,
                            TimeUnit.SECONDS,
                            AuthPhoneActivity.this,
                            mCallbacks

                    );
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
                    ToastCustom.showToast(1, AuthPhoneActivity.this,
                            AuthPhoneActivity.this.getLayoutInflater(),
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

                Intent otpIntent = new Intent(AuthPhoneActivity.this, OtpActivity.class);
                otpIntent.putExtra("AuthCredentials", s);
                otpIntent.putExtra("PhoneNumber", mCompletePhoneNumber);
                otpIntent.putExtra("firstName", firstName);
                otpIntent.putExtra("lastName", lastName);
                otpIntent.putExtra("email", email);
                otpIntent.putExtra("identityDocument", identityDocument);
                otpIntent.putExtra("address", address);
                otpIntent.putExtra("phone", phone);
                otpIntent.putExtra("birthDate", birthDate);
                otpIntent.putExtra("genero", genero);
                otpIntent.putExtra("codigoPais", codigoPais);
                otpIntent.putExtra("codigoDepartamento", codigoDepartamento);
                otpIntent.putExtra("codigoProvincia", codigoProvincia);
                otpIntent.putExtra("codigoDistrito", codigoDistrito);
                otpIntent.putExtra("password", password);
                otpIntent.putExtra("userRole", userRole);

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
        Intent homeIntent = new Intent(AuthPhoneActivity.this, ContentMainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
}

