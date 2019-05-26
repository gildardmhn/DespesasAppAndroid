package com.codinginflow.despesas.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codinginflow.despesas.Model.Usuario;
import com.codinginflow.despesas.R;
//import com.codinginflow.despesas.ViewModel.UsuarioViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class LoginActivity extends AppCompatActivity {

    public static final int ADD_USUARIO_LOGIN_REQUEST = 1;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private TextInputEditText email;
    private TextInputEditText input_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Entrar");

        inicializarFirebase();

        Button cadastrarUsuario = findViewById(R.id.cadastrarUsuario);
        cadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AddUsuarioActivity.class);
                startActivityForResult(intent, ADD_USUARIO_LOGIN_REQUEST);
            }
        });

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(LoginActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void btnMain(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_USUARIO_LOGIN_REQUEST && resultCode == RESULT_OK) {
            String nome = data.getStringExtra(AddUsuarioActivity.EXTRA_USU_NOME);
            String email = data.getStringExtra(AddUsuarioActivity.EXTRA_USU_EMAIL);
            String telefone = data.getStringExtra(AddUsuarioActivity.EXTRA_USU_TELEFONE);
            String senha = data.getStringExtra(AddUsuarioActivity.EXTRA_USU_SENHA);
            String hash = UUID.randomUUID().toString();

            Usuario usuario = new Usuario(nome, email, telefone, senha);
            usuario.setHash(hash);

            databaseReference.child("Usuario").child(usuario.getHash()).setValue(usuario);

            Toast.makeText(this, "Usuario salvo", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Usuario não salvo", Toast.LENGTH_SHORT).show();
        }
    }
}
