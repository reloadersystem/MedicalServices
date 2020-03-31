package com.salud.medicalservices.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Transition;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.salud.medicalservices.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    Animation animation;
    TextView tvSplash;
    ImageView ivLogo;

    private Transition transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tvSplash = findViewById(R.id.tvSplash);
        ivLogo = findViewById(R.id.ivLogo);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        tvSplash.startAnimation(animation);
        ivLogo.startAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {
            private static final long DURATION_TRANSITION = 1000;

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                SharedPreferences sharpref = getSharedPreferences("SharePreferenceGeneral", MODE_PRIVATE);

                if (sharpref.contains("userid")) {

                    Intent intent = new Intent(SplashActivity.this, AuthPhoneActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {

                    Intent intent = new Intent(SplashActivity.this, AuthPhoneActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
