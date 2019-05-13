package com.codinginflow.despesas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        setTitle("Cadastro");
    }

    public void btnLogin(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
