package com.salud.medicalservices.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.salud.medicalservices.R;
import com.salud.medicalservices.utils.MailJob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SendPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_enviar;
    TextInputLayout txt_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_enviar = findViewById(R.id.btn_enviar);
        txt_email = findViewById(R.id.edt_email);
        btn_enviar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_enviar:

                String txmail = txt_email.getEditText().getText().toString();

                if (txmail.isEmpty()) {
                    Toast.makeText(this, "Llena los  campos  requeridos", Toast.LENGTH_SHORT).show();
                } else {
                    new MailJob(getString(R.string.mailSendAdmin), getString(R.string.mailSendPass), this).execute(
                            new MailJob.Mail(getString(R.string.mailSendAdmin), getString(R.string.restore_email), "Restaurar Password", "Email to restaure:"+ txmail)
                    );

                    txt_email.getEditText().getText().clear();
                }
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}
