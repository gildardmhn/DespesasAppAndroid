package com.codinginflow.despesas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditEstabelecimentoActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.codinginflow.despesas.EXTRA_ID";
    public static final String EXTRA_NOME = "com.codinginflow.despesas.EXTRA_NOME";
    public static final String EXTRA_ENDERECO = "com.codinginflow.despesas.EXTRA_ENDERECO";
    public static final String EXTRA_TELEFONE = "com.codinginflow.despesas.EXTRA_TELEFONE";

    private EditText editTextNome;
    private EditText editTextEndereco;
    private EditText editTextTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_estabelecimento);

        editTextNome = findViewById(R.id.edit_text_est_nome);
        editTextEndereco = findViewById(R.id.edit_text_est_endereco);
        editTextTelefone = findViewById(R.id.edit_text_est_telefone);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Editar Estabelecimento");
            editTextNome.setText(intent.getStringExtra(EXTRA_NOME));
            editTextEndereco.setText(intent.getStringExtra(EXTRA_ENDERECO));
            editTextTelefone.setText(intent.getStringExtra(EXTRA_TELEFONE));
        } else {
            setTitle("Cadastrar Estabelecimento");
        }



    }

    private void saveEstabelecimento() {
        String nome = editTextNome.getText().toString();
        String endereco = editTextEndereco.getText().toString();
        String telefone = editTextTelefone.getText().toString();

        if(nome.trim().isEmpty() || endereco.trim().isEmpty() || telefone.trim().isEmpty()){
            Toast.makeText(this, "Preencha as informações", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();

        data.putExtra(EXTRA_NOME, nome);
        data.putExtra(EXTRA_ENDERECO, endereco);
        data.putExtra(EXTRA_TELEFONE, telefone);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_estabelecimento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_estabelecimento:
                saveEstabelecimento();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
