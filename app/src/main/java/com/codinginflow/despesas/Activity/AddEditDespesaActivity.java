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


public class AddEditDespesaActivity extends AppCompatActivity {

    public static final String EXTRA_HASH = "com.codinginflow.despesas.EXTRA_HASH";
    public static final String EXTRA_TITULO = "com.codinginflow.despesas.EXTRA_TITULO";
    public static final String EXTRA_DESCRICAO = "com.codinginflow.despesas.EXTRA_DESCRICAO";
    public static final String EXTRA_TIPO = "com.codinginflow.despesas.EXTRA_TIPO";
    public static final String EXTRA_PRECO = "com.codinginflow.despesas.EXTRA_PRECO";

    private EditText editTextTitulo;
    private EditText editTextDescricao;
    private EditText editTextTipo;
    private EditText editTextPreco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_despesa);

        editTextTitulo = findViewById(R.id.edit_text_titulo);
        editTextDescricao = findViewById(R.id.edit_text_descricao);
        editTextTipo = findViewById(R.id.edit_text_tipo);
        editTextPreco = findViewById(R.id.edit_text_preco);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_HASH)){
            setTitle("Editar Despesa");
            editTextTitulo.setText(intent.getStringExtra(EXTRA_TITULO));
            editTextDescricao.setText(intent.getStringExtra(EXTRA_DESCRICAO));
            editTextTipo.setText(intent.getStringExtra(EXTRA_TIPO));
            editTextPreco.setText(intent.getStringExtra(EXTRA_PRECO));
        } else {
            setTitle("Cadastrar Despesa");
        }

    }
    private void delteDespesa() {
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_HASH)){
            String hash = intent.getStringExtra(EXTRA_HASH);
            Intent deleteHash = new Intent();
            deleteHash.putExtra(EXTRA_HASH, hash);
            setResult(RESULT_OK, deleteHash);
            finish();
        }else {
            Toast.makeText(this, "Não pode remover uma despesa inexistante", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDespesa() {
        String titulo = editTextTitulo.getText().toString();
        String descricao = editTextDescricao.getText().toString();
        String tipo = editTextTipo.getText().toString();
        Double preco = Double.parseDouble(editTextPreco.getText().toString());

        if (titulo.trim().isEmpty() || descricao.trim().isEmpty() || tipo.trim().isEmpty() || preco.isNaN()) {
            Toast.makeText(this, "Preencha as informações", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITULO, titulo);
        data.putExtra(EXTRA_DESCRICAO, descricao);
        data.putExtra(EXTRA_TIPO, tipo);
        data.putExtra(EXTRA_PRECO, preco);

        String hash = getIntent().getStringExtra(EXTRA_HASH);
        if(hash != null){
            data.putExtra(EXTRA_HASH, hash);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_despesa_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_despesa:
                saveDespesa();
                return true;

            case R.id.delete_despesa:
                delteDespesa();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
