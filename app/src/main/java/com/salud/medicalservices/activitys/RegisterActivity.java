package com.salud.medicalservices.activitys;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import com.salud.medicalservices.utils.Tools;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RegisterActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button mCreateAccountBtn;
    EditText edt_mail, edt_nombres, edt_apellidos, edt_password, edt_phone;
    Spinner spinner_document, spinner_sexo;
    String spinnerDocumento, spinnerSexo;
    TextView edt_documento, edt_birthday;
    EditText edt_direccion;

    TextView txt_codigopais, txt_departamento, txt_distrito, txt_provincia;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.toolbar);
        mCreateAccountBtn = findViewById(R.id.btnCreateAccount);

        edt_mail = findViewById(R.id.edt_mail);
        edt_nombres = findViewById(R.id.edt_nombres);
        edt_apellidos = findViewById(R.id.edt_apellidos);
        edt_password = findViewById(R.id.edt_password);
        edt_birthday = findViewById(R.id.edt_birthday);
        edt_phone = findViewById(R.id.edt_phone);
        spinner_document = findViewById(R.id.spinner_document);
        spinner_sexo = findViewById(R.id.spinner_sexo);
        edt_documento = findViewById(R.id.edt_documento);
        edt_direccion = findViewById(R.id.edt_direccion);

        txt_codigopais = findViewById(R.id.txt_codigopais);
        txt_departamento = findViewById(R.id.txt_departamento);
        txt_distrito = findViewById(R.id.txt_distrito);
        txt_provincia = findViewById(R.id.txt_provincia);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Complete el formulario");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);


        //902293313

        //Setting the ArrayAdapter data on the Spinner

        spinner_document.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                spinnerDocumento = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_sexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinnerSexo = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edt_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog,
                        setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                datePickerDialog.show();

            }
        });


        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                String date = day + "/" + month + "/" + year;
                edt_birthday.setText("  " + date);


            }
        };


        mCreateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate();


            }
        });
    }

    private void validate() {

        String firstName = edt_nombres.getText().toString().trim();
        String lastName = edt_mail.getText().toString().trim();
        String email = edt_apellidos.getText().toString().trim();
        String identityDocument = edt_documento.getText().toString().trim();
        String address = edt_direccion.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        String phone = edt_phone.getText().toString().trim();
        String birthDate = edt_birthday.getText().toString().trim();
        String codigoDepartamento = txt_departamento.getText().toString().trim();
        String codigoDistrito = txt_distrito.getText().toString().trim();
        String codigoProvincia = txt_provincia.getText().toString().trim();
        String codigoPais = txt_codigopais.getText().toString().trim();
        String genero = spinnerSexo;
        String userRole = "User";

        Intent intent = new Intent(RegisterActivity.this, AuthPhoneActivity.class);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("email", email);
        intent.putExtra("identityDocument", identityDocument);
        intent.putExtra("address", address);
        intent.putExtra("phone", phone);
        intent.putExtra("birthDate", birthDate);
        intent.putExtra("codigoDepartamento", codigoDepartamento);
        intent.putExtra("codigoDistrito", codigoDistrito);
        intent.putExtra("codigoProvincia", codigoProvincia);
        intent.putExtra("codigoPais", codigoPais);
        intent.putExtra("genero", genero);
        intent.putExtra("password", password);
        intent.putExtra("userRole", userRole);
        startActivity(intent);
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
