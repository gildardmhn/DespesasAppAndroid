package com.codinginflow.despesas.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codinginflow.despesas.Model.Usuario;
import com.codinginflow.despesas.R;
import com.codinginflow.despesas.ViewModel.UsuarioViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    public static final int ADD_USUARIO_LOGIN_REQUEST = 1;
    private UsuarioViewModel usuarioViewModel;

    private TextInputEditText email;
    private TextInputEditText input_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Entrar");

        Button cadastrarUsuario = findViewById(R.id.cadastrarUsuario);
        cadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivityForResult(intent, ADD_USUARIO_LOGIN_REQUEST);
            }
        });

        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel.class);
    }

    public void btnMain(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_USUARIO_LOGIN_REQUEST && resultCode == RESULT_OK){
            String nome = data.getStringExtra(CadastroActivity.EXTRA_USU_NOME);
            String email = data.getStringExtra(CadastroActivity.EXTRA_EMAIL);
            String telefone = data.getStringExtra(CadastroActivity.EXTRA_USU_TELEFONE);
            String senha = data.getStringExtra(CadastroActivity.EXTRA_SENHA);

            Usuario usuario = new Usuario(nome, email, telefone, senha);
            usuarioViewModel.insert(usuario);

            Toast.makeText(this, "Usuario salvo", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Usuario não salvo", Toast.LENGTH_SHORT).show();
        }
    }
}
