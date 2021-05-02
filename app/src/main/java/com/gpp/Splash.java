package com.gpp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    TextView ed,sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        ed = (TextView) findViewById(R.id.big);
        sm = (TextView) findViewById(R.id.small);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ed.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
                sm.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
                Intent i = new Intent(Splash.this,otp.class);
                startActivity(i);
                finish();
            }
        }, 3000);

    }
}