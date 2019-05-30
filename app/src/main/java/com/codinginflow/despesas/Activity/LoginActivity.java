package com.codinginflow.despesas.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    public static final int ADD_USUARIO_LOGIN_REQUEST = 1;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

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


        inicializarCampos(); // finds...
        inicializarFirebase();
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

        auth = FirebaseAuth.getInstance();
    }

    public void btnLogin(View view) {
        String email = inputEmail.getText().toString().trim();
        String senha = inputSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha os campos!", Toast.LENGTH_SHORT).show();
        } else {
            login(email, senha);
        }
    }

    public void btnCadastro(View view) {
        Intent intent = new Intent(getApplicationContext(), AddUsuarioActivity.class);
        startActivity(intent);
    }

    private void login(String email, String senha) {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                System.out.println(task.getException());

                if (!task.isSuccessful()) {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        inputEmail.setError("Email inválido!");
                        inputEmail.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        inputSenha.setError("Senha inválida!");
                        inputSenha.requestFocus();
                    } catch (FirebaseNetworkException e) {
                        Toast.makeText(LoginActivity.this, "Erro de conexão!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    Toast.makeText(LoginActivity.this, "Email ou senha inválidos!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
