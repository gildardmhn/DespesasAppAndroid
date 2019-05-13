package com.codinginflow.despesas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Entrar");
    }

    public void btnCadastro(View view) {
        startActivity(new Intent(getApplicationContext(), CadastroActivity.class));
    }
}
