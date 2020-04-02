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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.salud.medicalservices.R;
import com.salud.medicalservices.entidades.Departamento;
import com.salud.medicalservices.entidades.Distrito;
import com.salud.medicalservices.entidades.Pais;
import com.salud.medicalservices.entidades.PrioridadSpinner;
import com.salud.medicalservices.entidades.Provincia;
import com.salud.medicalservices.entidades.response.ResponseDepartamento;
import com.salud.medicalservices.entidades.response.ResponseDistrito;
import com.salud.medicalservices.entidades.response.ResponsePaises;
import com.salud.medicalservices.entidades.response.ResponseProvincia;
import com.salud.medicalservices.networking.EndPoint;
import com.salud.medicalservices.networking.MethodWs;
import com.salud.medicalservices.utils.Tools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements Validator.ValidationListener {
    Toolbar toolbar;
    Button mCreateAccountBtn;

    @Required(order = 1, message = "Debe ingresar sus nombres")
    EditText edt_nombres;

    @Required(order = 2, message = "Debe ingresar sus apellidos")
    EditText edt_apellidos;

    @Required(order = 3, message = "Debe ingresar su correo")
    @Email(order = 4, message = "Debe ingresar un correo valido")
    EditText edt_mail;

    @Required(order = 5, message = "Debe ingresar su clave")
    EditText edt_password;

    @Required(order = 6, message = "Debe ingresar su documento")
    EditText edt_documento;

    @Required(order = 7, message = "Debe ingresar su direccion")
    EditText edt_direccion;

    @Required(order = 8, message = "Debe ingresar su celular")
    EditText edt_phone;



    Spinner spinner_document, spinner_sexo;
    String spinnerDocumento, spinnerSexo;
    TextView edt_birthday;

    TextView txt_codigopais, txt_departamento, txt_distrito, txt_provincia;

    Spinner sp_pais,sp_departamento,sp_provincia,sp_distrito;

    DatePickerDialog.OnDateSetListener setListener;

    SweetAlertDialog pd;

    String id_pais,nombre_pais,id_departamento,nombre_departamento,
    id_provincia,nombre_provincia,id_distrito,nombre_distrito;

    Validator validator;

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

        //txt_codigopais = findViewById(R.id.txt_codigopais);
        //txt_departamento = findViewById(R.id.txt_departamento);
        //txt_distrito = findViewById(R.id.txt_distrito);
        //txt_provincia = findViewById(R.id.txt_provincia);

        sp_pais =  findViewById(R.id.sp_pais);
        sp_departamento =  findViewById(R.id.sp_departamento);
        sp_provincia =  findViewById(R.id.sp_provincia);
        sp_distrito =  findViewById(R.id.sp_distrito);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Complete el formulario");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);

        validator = new Validator(this);
        validator.setValidationListener(this);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);


        callApiPaises();
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

                validator.validate();


            }
        });

        sp_pais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                id_pais = ((PrioridadSpinner) parent.getItemAtPosition(position)).id;
                nombre_pais = ((PrioridadSpinner) parent.getItemAtPosition(position)).name;

                /*pd = new SweetAlertDialog(RegisterActivity.this,SweetAlertDialog.PROGRESS_TYPE);
                pd.getProgressHelper().setBarColor(Color.parseColor("#102670"));
                pd.setContentText("Por favor, espere...");
                pd.setCancelable(false);
                pd.show();*/


                EndPoint endPoint = MethodWs.getConfiguration().create(EndPoint.class);
                Call<ResponseDepartamento> response = endPoint.obtenerDepartamentos(id_pais);
                response.enqueue(new Callback<ResponseDepartamento>() {
                    @Override
                    public void onResponse(Call<ResponseDepartamento> call, Response<ResponseDepartamento> response) {

                        if (response.isSuccessful()){

                            ResponseDepartamento responseDepartamento = response.body();

                            processOnResponseDepartamento(responseDepartamento.getItems(), sp_departamento, "Seleccione departamento");
                            //pd.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseDepartamento> call, Throwable t) {

                        pd.dismiss();
                        Toast.makeText(RegisterActivity.this,t.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                });




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_departamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                id_departamento = ((PrioridadSpinner) parent.getItemAtPosition(position)).id;
                nombre_departamento = ((PrioridadSpinner) parent.getItemAtPosition(position)).name;

                /*pd = new SweetAlertDialog(RegisterActivity.this,SweetAlertDialog.PROGRESS_TYPE);
                pd.getProgressHelper().setBarColor(Color.parseColor("#102670"));
                pd.setContentText("Por favor, espere...");
                pd.setCancelable(false);
                pd.show();*/


                EndPoint endPoint = MethodWs.getConfiguration().create(EndPoint.class);
                Call<ResponseProvincia> response = endPoint.obtenerProvincias(id_pais,id_departamento);
                response.enqueue(new Callback<ResponseProvincia>() {
                    @Override
                    public void onResponse(Call<ResponseProvincia> call, Response<ResponseProvincia> response) {

                        if (response.isSuccessful()){

                            ResponseProvincia responseProvincia = response.body();

                            processOnResponseProvincia(responseProvincia.getItems(), sp_provincia, "Seleccione provincia");
                            //pd.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseProvincia> call, Throwable t) {

                        pd.dismiss();
                        Toast.makeText(RegisterActivity.this,t.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                });




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                id_provincia = ((PrioridadSpinner) parent.getItemAtPosition(position)).id;
                nombre_provincia = ((PrioridadSpinner) parent.getItemAtPosition(position)).name;

                /*pd = new SweetAlertDialog(RegisterActivity.this,SweetAlertDialog.PROGRESS_TYPE);
                pd.getProgressHelper().setBarColor(Color.parseColor("#102670"));
                pd.setContentText("Por favor, espere...");
                pd.setCancelable(false);
                pd.show();*/

                EndPoint endPoint = MethodWs.getConfiguration().create(EndPoint.class);
                Call<ResponseDistrito> response = endPoint.obtenerDistritos(id_pais,id_departamento,id_provincia);
                response.enqueue(new Callback<ResponseDistrito>() {
                    @Override
                    public void onResponse(Call<ResponseDistrito> call, Response<ResponseDistrito> response) {

                        if (response.isSuccessful()){

                            ResponseDistrito responseDistrito = response.body();

                            processOnResponseDistrito(responseDistrito.getItems(), sp_distrito, "Seleccione distrito");
                            //pd.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseDistrito> call, Throwable t) {

                        pd.dismiss();
                        Toast.makeText(RegisterActivity.this,t.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                });




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                id_distrito = ((PrioridadSpinner) parent.getItemAtPosition(position)).id;
                nombre_distrito = ((PrioridadSpinner) parent.getItemAtPosition(position)).name;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void callApiPaises() {

        pd = new SweetAlertDialog(RegisterActivity.this,SweetAlertDialog.PROGRESS_TYPE);
        pd.getProgressHelper().setBarColor(Color.parseColor("#102670"));
        pd.setContentText("Por favor, espere...");
        pd.setCancelable(false);
        pd.show();


        EndPoint endPoint = MethodWs.getConfiguration().create(EndPoint.class);
        Call<ResponsePaises> response = endPoint.obtenerPaises();
        response.enqueue(new Callback<ResponsePaises>() {
            @Override
            public void onResponse(Call<ResponsePaises>call, Response<ResponsePaises> response) {

                if (response.isSuccessful()){

                    ResponsePaises responsePaises = response.body();

                    processOnResponsePais(responsePaises.getItems(), sp_pais, "Seleccione pais");
                    pd.dismiss();

                }
            }

            @Override
            public void onFailure(Call<ResponsePaises> call, Throwable t) {

                pd.dismiss();
                Toast.makeText(RegisterActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                //pd.dismiss();
            }
        });
    }

    public void processOnResponsePais(ArrayList<Pais> result, Spinner spinner, String label) {
        LinkedList locations = new LinkedList();

        PrioridadSpinner prioridadSpinnerLabel = new PrioridadSpinner();
        prioridadSpinnerLabel.name = label;
        prioridadSpinnerLabel.id = "-1";
        locations.add(prioridadSpinnerLabel);


        for (Pais itemLocation : result) {
            PrioridadSpinner locationSpinner = new PrioridadSpinner();
            locationSpinner.name = itemLocation.getName();
            locationSpinner.id = itemLocation.getId();

            locations.add(locationSpinner);

        }

        //ArrayAdapter spinner_adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, locations);
        ArrayAdapter spinner_adapter = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_item, locations);
        //Añadimos el layout para el menú y se lo damos al spinner
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
        spinner.setAdapter(spinner_adapter);
    }

    public void processOnResponseDepartamento(ArrayList<Departamento> result, Spinner spinner, String label) {
        LinkedList locations = new LinkedList();

        PrioridadSpinner prioridadSpinnerLabel = new PrioridadSpinner();
        prioridadSpinnerLabel.name = label;
        prioridadSpinnerLabel.id = "-1";
        locations.add(prioridadSpinnerLabel);


        for (Departamento itemLocation : result) {
            PrioridadSpinner locationSpinner = new PrioridadSpinner();
            locationSpinner.name = itemLocation.getName();
            locationSpinner.id = itemLocation.getId();

            locations.add(locationSpinner);

        }

        //ArrayAdapter spinner_adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, locations);
        ArrayAdapter spinner_adapter = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_item, locations);
        //Añadimos el layout para el menú y se lo damos al spinner
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
        spinner.setAdapter(spinner_adapter);
    }

    public void processOnResponseProvincia(ArrayList<Provincia> result, Spinner spinner, String label) {
        LinkedList locations = new LinkedList();

        PrioridadSpinner prioridadSpinnerLabel = new PrioridadSpinner();
        prioridadSpinnerLabel.name = label;
        prioridadSpinnerLabel.id = "-1";
        locations.add(prioridadSpinnerLabel);


        for (Provincia itemLocation : result) {
            PrioridadSpinner locationSpinner = new PrioridadSpinner();
            locationSpinner.name = itemLocation.getName();
            locationSpinner.id = itemLocation.getId();

            locations.add(locationSpinner);

        }

        //ArrayAdapter spinner_adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, locations);
        ArrayAdapter spinner_adapter = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_item, locations);
        //Añadimos el layout para el menú y se lo damos al spinner
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
        spinner.setAdapter(spinner_adapter);
    }

    public void processOnResponseDistrito(ArrayList<Distrito> result, Spinner spinner, String label) {
        LinkedList locations = new LinkedList();

        PrioridadSpinner prioridadSpinnerLabel = new PrioridadSpinner();
        prioridadSpinnerLabel.name = label;
        prioridadSpinnerLabel.id = "-1";
        locations.add(prioridadSpinnerLabel);


        for (Distrito itemLocation : result) {
            PrioridadSpinner locationSpinner = new PrioridadSpinner();
            locationSpinner.name = itemLocation.getName();
            locationSpinner.id = itemLocation.getId();

            locations.add(locationSpinner);

        }

        //ArrayAdapter spinner_adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, locations);
        ArrayAdapter spinner_adapter = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_item, locations);
        //Añadimos el layout para el menú y se lo damos al spinner
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
        spinner.setAdapter(spinner_adapter);
    }

    private void validate() {

        if (id_pais.equals("-1")){

            pd = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE);
            pd.getProgressHelper().setBarColor(Color.parseColor("#102670"));
            pd.setContentText("Seleccionar pais");
            pd.setCancelable(false);
            pd.show();
            return;
        }

        if (id_departamento.equals("-1")){

            pd = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE);
            pd.getProgressHelper().setBarColor(Color.parseColor("#102670"));
            pd.setContentText("Seleccionar departamento");
            pd.setCancelable(false);
            pd.show();
            return;
        }

        if (id_provincia.equals("-1")){

            pd = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE);
            pd.getProgressHelper().setBarColor(Color.parseColor("#102670"));
            pd.setContentText("Seleccionar provincia");
            pd.setCancelable(false);
            pd.show();
            return;
        }

        if (id_distrito.equals("-1")){

            pd = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE);
            pd.getProgressHelper().setBarColor(Color.parseColor("#102670"));
            pd.setContentText("Seleccionar distrito");
            pd.setCancelable(false);
            pd.show();
            return;
        }

        String firstName = edt_nombres.getText().toString().trim();
        String lastName = edt_mail.getText().toString().trim();
        String email = edt_apellidos.getText().toString().trim();
        String identityDocument = edt_documento.getText().toString().trim();
        String address = edt_direccion.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        String phone = edt_phone.getText().toString().trim();
        String birthDate = edt_birthday.getText().toString().trim();
        /*String codigoDepartamento = txt_departamento.getText().toString().trim();
        String codigoDistrito = txt_distrito.getText().toString().trim();
        String codigoProvincia = txt_provincia.getText().toString().trim();
        String codigoPais = txt_codigopais.getText().toString().trim();*/
        String codigoDepartamento = id_departamento;
        String codigoDistrito = id_distrito;
        String codigoProvincia = id_provincia;
        String codigoPais = id_pais;
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

    @Override
    public void onValidationSucceeded() {

        validate();
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

        final String failureMessage = failedRule.getFailureMessage();
        if (failedView instanceof EditText) {
            EditText failed = (EditText) failedView;
            failed.requestFocus();
            failed.setError(failureMessage);
        } else {
            Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
