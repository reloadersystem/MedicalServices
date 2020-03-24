package com.salud.medicalservices.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.salud.medicalservices.R;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txv_sendpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txv_sendpassword = findViewById(R.id.txr_review_password);

        txv_sendpassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.txr_review_password:

                Intent intent = new Intent(LoginActivity.this, SendPasswordActivity.class);
                startActivity(intent);



                break;
        }
    }
}
