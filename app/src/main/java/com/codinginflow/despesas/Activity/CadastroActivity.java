package com.codinginflow.despesas.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.codinginflow.despesas.R;

public class CadastroActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.codinginflow.despesas.EXTRA_ID";
    public static final String EXTRA_USU_NOME = "com.codinginflow.despesas.EXTRA_USU_NOME";
    public static final String EXTRA_EMAIL = "com.codinginflow.despesas.EXTRA_EMAIL";
    public static final String EXTRA_USU_TELEFONE= "com.codinginflow.despesas.EXTRA_USU_TELEFONE";
    public static final String EXTRA_SENHA = "com.codinginflow.despesas.EXTRA_SENHA";

    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextTelefone;
    private EditText editTextSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        editTextNome = findViewById(R.id.edit_text_usu_nome);
        editTextEmail = findViewById(R.id.edit_text_usu_email);
        editTextTelefone = findViewById(R.id.edit_text_usu_telefone);
        editTextSenha = findViewById(R.id.edit_text_usu_senha);

        setTitle("Cadastro de usuário");


    }

    private void saveUsuario() {
        String nome = editTextNome.getText().toString();
        String email = editTextEmail.getText().toString();
        String telefone = editTextTelefone.getText().toString();
        String senha = editTextSenha.getText().toString();

        if (nome.trim().isEmpty() || email.trim().isEmpty() || telefone.trim().isEmpty() || senha.trim().isEmpty()){
            Toast.makeText(this, "Preencha as informações", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_USU_NOME, nome);
        data.putExtra(EXTRA_EMAIL, email);
        data.putExtra(EXTRA_USU_TELEFONE, telefone);
        data.putExtra(EXTRA_SENHA, senha);

        setResult(RESULT_OK, data);
//        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_usuario_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_usuario:
                saveUsuario();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
