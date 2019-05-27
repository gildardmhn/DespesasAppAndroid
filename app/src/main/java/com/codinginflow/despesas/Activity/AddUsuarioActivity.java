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

    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextTelefone;
    private EditText editTextAntigaSenha;
    private EditText editTextSenha;
    private EditText editTextConfirmaSenha;
    private Button loginButton;
    private Button cadastrarButton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        cadastrarButton = findViewById(R.id.btn_cadastrarUsuario);
        loginButton = findViewById(R.id.btn_loginUsuario);
//        editTextNome = findViewById(R.id.edit_text_usu_nome);
        editTextEmail = findViewById(R.id.edit_text_usu_email);
//        editTextTelefone = findViewById(R.id.edit_text_usu_telefone);
//        editTextAntigaSenha = findViewById(R.id.edit_text_usu_antiga_senha);
        editTextSenha = findViewById(R.id.edit_text_usu_senha);
        editTextConfirmaSenha = findViewById(R.id.edit_text_usu_confirma_senha);
//        editTextAntigaSenha.setText("nada");
//        editTextAntigaSenha.setVisibility(View.GONE);

        eventoClicks();

//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

//        cadastrarButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveUsuario();
//            }
//        });

        setTitle("Cadastro de usuário");
//
//        if (getIntent().hasExtra(EXTRA_USU_HASH)) {
//            setTitle("Alterar dados");
//            cadastrarButton.setText("Atualizar dados");
//            editTextEmail.setEnabled(false);
//            loginButton.setVisibility(View.GONE);
//
//            editTextAntigaSenha.setVisibility(View.VISIBLE);
//            editTextAntigaSenha.setText("");
//            editTextEmail.setText(getIntent().getStringExtra(EXTRA_USU_EMAIL));
//            editTextNome.setText(getIntent().getStringExtra(EXTRA_USU_NOME));
//            editTextTelefone.setText(getIntent().getStringExtra(EXTRA_USU_TELEFONE));
//        }


    }

    private void eventoClicks() {
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editTextNome.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String telefone = editTextTelefone.getText().toString().trim();
                String senha = editTextSenha.getText().toString().trim();

                criarUsuario(email, senha);
            }
        });
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

    private void verificarSalva(String senha, String nome, String email, String telefone, String confirmaSenha) {
        if (senha.equals(confirmaSenha)) {
            Intent data = new Intent();
            data.putExtra(EXTRA_USU_NOME, nome);
            data.putExtra(EXTRA_USU_EMAIL, email);
            data.putExtra(EXTRA_USU_TELEFONE, telefone);
            data.putExtra(EXTRA_USU_SENHA, senha);

            String hash = getIntent().getStringExtra(EXTRA_USU_HASH);
            if (hash != null) {
                data.putExtra(EXTRA_USU_HASH, hash);
            }

            setResult(RESULT_OK, data);
            finish();
        } else {
            Toast.makeText(this, "Senha não identica", Toast.LENGTH_SHORT).show();
        }
    }

//    private void saveUsuario() {
//
//        String nome = editTextNome.getText().toString();
//        String email = editTextEmail.getText().toString();
//        String telefone = editTextTelefone.getText().toString();
//        String antigaSenha = editTextAntigaSenha.getText().toString();
//        String senha = editTextSenha.getText().toString();
//        String confirmaSenha = editTextConfirmaSenha.getText().toString();
//
//        if (nome.trim().isEmpty() || email.trim().isEmpty() || telefone.trim().isEmpty() ||
//                antigaSenha.trim().isEmpty() || senha.trim().isEmpty() || confirmaSenha.trim().isEmpty()) {
//            Toast.makeText(this, "Preencha as informações", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (antigaSenha.equals(getIntent().getStringExtra(EXTRA_USU_SENHA))) {
//            verificarSalva(senha, nome, email, telefone, confirmaSenha);
//        } else if (antigaSenha.equals("nada")) {
//            verificarSalva(senha, nome, email, telefone, confirmaSenha);
//        } else {
//            Toast.makeText(this, "A antiga senha não corresponde", Toast.LENGTH_SHORT).show();
//        }
//
//    }
}