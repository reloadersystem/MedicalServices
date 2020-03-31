package com.salud.medicalservices.activitys;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.salud.medicalservices.R;
import com.salud.medicalservices.utils.ShareDataRead;
import com.salud.medicalservices.utils.Tools;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RegisterActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button mCreateAccountBtn;
    EditText edt_mail, edt_nombres, edt_appaterno;
    Spinner spinner_document;
    String dataSpinner;
    TextView edt_documento, edt_birthday;
    EditText edt_direccion;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.toolbar);
        mCreateAccountBtn = findViewById(R.id.btnCreateAccount);

        edt_mail = findViewById(R.id.edt_mail);
        edt_nombres = findViewById(R.id.edt_nombres);
        edt_appaterno = findViewById(R.id.edt_appaterno);
        edt_birthday = findViewById(R.id.edt_birthday);

        spinner_document = findViewById(R.id.spinner_document);
        edt_documento = findViewById(R.id.edt_documento);
        edt_direccion = findViewById(R.id.edt_direccion);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Complete el formulario");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);

        //Setting the ArrayAdapter data on the Spinner

        spinner_document.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                dataSpinner = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edt_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                datePickerDialog.show();

            }
        });


        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                String date = day + "/" + month + "/" + year;
                edt_birthday.setText("  "+ date);


            }
        };


        mCreateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = edt_nombres.getText().toString().trim();
                String userMail = edt_mail.getText().toString().trim();
                String userPaterno = edt_appaterno.getText().toString().trim();

                String documento = edt_documento.getText().toString().trim();
                String direccion = edt_direccion.getText().toString().trim();

                String phoneNumber = ShareDataRead.obtenerValor(getApplicationContext(), "PhoneNumber");

//                RegisterUser registerUser = new RegisterUser(phoneNumber, userName + " " + userPaterno + " " + userMaterno, userPaterno, userMaterno, userMail, direccion, "-1215645641", dataSpinner, documento, "APPC");
//                MethodWs methodWs = HelperWs.getConfiguration(getApplicationContext()).create(MethodWs.class);
//                Call<ResponseBody> responseBodyCall = methodWs.postRegistrar(registerUser);
//                responseBodyCall.enqueue(new Callback<ResponseBody>() {
//
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        ResponseBody informacion = response.body();
//
//                        if (response.isSuccessful()) {
//                            try {
//                                String cadena_respuesta = informacion.string();
//                                Log.v("RsptaResponsePost", cadena_respuesta);
//                                JSONObject jsonObject = new JSONObject(cadena_respuesta);
//                                int resp = jsonObject.getInt("RespuestaCodigo");
//
//                                if (resp > 0) {
//                                    guardarValor(getApplicationContext(), "userid", userName);
//                                    guardarValor(getApplicationContext(), "userMail", userMail);
//                                    guardarValor(getApplicationContext(), "userMail", userMail);
//                                    guardarValor(getApplicationContext(), "NombreCompleto", userName);
//                                    guardarValor(getApplicationContext(), "userPaterno", userPaterno);
//                                    guardarValor(getApplicationContext(), "userMaterno", userMaterno);
//
//                                    Intent homeIntent = new Intent(RegisterActivity.this, MainActivity.class);
//                                    startActivity(homeIntent);
//                                    finish();
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            Log.e("infoResponsePost", informacion.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.e("infoResponseErrorPost", t.getMessage());
//                    }
//                });
            }
        });
    }


    private static String PREFS_KEY = "UserLoginPref";

    public static void guardarValor(Context context, String keyPref, String valor) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putString(keyPref, valor);
        editor.commit();
    }


}
