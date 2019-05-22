package com.codinginflow.despesas.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.codinginflow.despesas.R;

public class SplashActivity extends AppCompatActivity {

    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv = (TextView) findViewById(R.id.splash_texto);
        iv = (ImageView) findViewById(R.id.splash_logo);
        Animation mAnimation = AnimationUtils.loadAnimation(this, R.anim.transicao);
        tv.startAnimation(mAnimation);
        iv.startAnimation(mAnimation);

        // ou MainActivity.class
        final Intent i = new Intent(this, SplashActivity.class);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
