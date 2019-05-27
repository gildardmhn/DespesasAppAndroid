package com.codinginflow.despesas.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codinginflow.despesas.Conexao.Conexao;
import com.codinginflow.despesas.R;
//import com.codinginflow.despesas.ViewModel.UsuarioViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    public static final int ADD_USUARIO_LOGIN_REQUEST = 1;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private TextInputEditText email;
    private TextInputEditText input_text;

    private TextInputEditText inputEmail;
    private TextInputEditText inputSenha;

    private Button btnCadastro;
    private Button btnLogin;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Entrar");


        inicializarCampos();
        inicializarFirebase();


//        btnCadastro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, AddUsuarioActivity.class);
//                startActivityForResult(intent, ADD_USUARIO_LOGIN_REQUEST);
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }

    protected void inicializarCampos() {
        inputEmail = findViewById(R.id.input_email);
        inputSenha = findViewById(R.id.input_senha);

        btnLogin = findViewById(R.id.btn_login);
        btnCadastro = findViewById(R.id.btn_cadastro);
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(LoginActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void btnLogin(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    private void eventoClicks() {
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddUsuarioActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String senha = inputEmail.getText().toString().trim();
                login(email, senha);
            }
        });
    }

    private void login(String email, String senha) {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Email ou senha errados!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == ADD_USUARIO_LOGIN_REQUEST && resultCode == RESULT_OK) {
//            String nome = data.getStringExtra(AddUsuarioActivity.EXTRA_USU_NOME);
//            String email = data.getStringExtra(AddUsuarioActivity.EXTRA_USU_EMAIL);
//            String telefone = data.getStringExtra(AddUsuarioActivity.EXTRA_USU_TELEFONE);
//            String senha = data.getStringExtra(AddUsuarioActivity.EXTRA_USU_SENHA);
//            String hash = UUID.randomUUID().toString();
//
//            Usuario usuario = new Usuario(nome, email, telefone, senha);
//            usuario.setHash(hash);
//
//            databaseReference.child("Usuario").child(usuario.getHash()).setValue(usuario);
//
//            Toast.makeText(this, "Usuario salvo", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Usuario não salvo", Toast.LENGTH_SHORT).show();
//        }
//    }
}
