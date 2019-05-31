package com.codinginflow.despesas.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Desativar ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Inicializando...");
        mProgress.setMessage("Prepare o seu bolso...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        tv = (TextView) findViewById(R.id.splash_texto);
        iv = (ImageView) findViewById(R.id.splash_logo);
        Animation mAnimation = AnimationUtils.loadAnimation(this, R.anim.transicao);
        tv.startAnimation(mAnimation);
        iv.startAnimation(mAnimation);

        // ou MainActivity.class
        final Intent i = new Intent(this, LoginActivity.class);

//        mProgress.show();
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
//                    mProgress.dismiss();
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
