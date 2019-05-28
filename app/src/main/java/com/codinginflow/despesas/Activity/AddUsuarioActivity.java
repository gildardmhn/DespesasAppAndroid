package com.codinginflow.despesas.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codinginflow.despesas.Conexao.Conexao;
import com.codinginflow.despesas.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AddUsuarioActivity extends AppCompatActivity {
    public static final String EXTRA_USU_HASH = "com.codinginflow.despesas.EXTRA_USU_HASH";
    public static final String EXTRA_USU_NOME = "com.codinginflow.despesas.EXTRA_USU_NOME";
    public static final String EXTRA_USU_EMAIL = "com.codinginflow.despesas.EXTRA_USU_EMAIL";
    public static final String EXTRA_USU_TELEFONE = "com.codinginflow.despesas.EXTRA_USU_TELEFONE";
    public static final String EXTRA_USU_SENHA = "com.codinginflow.despesas.EXTRA_USU_SENHA";

    private EditText editTextEmail;
    private EditText editTextSenha;
    private EditText editTextConfirmaSenha;

    private Button loginButton;
    private Button cadastrarButton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        setTitle("Cadastro de usuário");

        inicializarCampos();
        // eventoClicks();
    }

    private void inicializarCampos() {
        cadastrarButton = findViewById(R.id.btn_cadastrarUsuario);
        loginButton = findViewById(R.id.btn_loginUsuario);

        editTextEmail = findViewById(R.id.edit_text_email);
        editTextSenha = findViewById(R.id.edit_text_senha);
        editTextConfirmaSenha = findViewById(R.id.edit_text_confirma_senha);
    }

    private void eventoClicks() {
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String senha = editTextSenha.getText().toString().trim();
                String confirmaSenha = editTextConfirmaSenha.getText().toString().trim();

                verificaSenha(senha, confirmaSenha);

                criarUsuario(email, senha);
            }
        });
    }

    public void btnCadastrar(View view) {
        String email = editTextEmail.getText().toString().trim();
        String senha = editTextSenha.getText().toString().trim();
        String confirmaSenha = editTextConfirmaSenha.getText().toString().trim();

        verificaSenha(senha, confirmaSenha);

        criarUsuario(email, senha);
    }

    public void btnEntrar(View view) {
        Intent intent = new Intent(AddUsuarioActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void criarUsuario(String email, String senha) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(AddUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    alert("Usuário cadastrado com sucesso!");
                    Intent intent = new Intent(AddUsuarioActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    alert("Usuário não cadastrado!");
                }
            }
        });
    }

    private void alert(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }

    private void verificaSenha(String senha, String confirmaSenha) {
        if (!senha.equals(confirmaSenha)) {
            Toast.makeText(this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
        }
    }
}
