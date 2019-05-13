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

    public static final String EXTRA_ID = "com.codinginflow.despesas.EXTRA_ID";
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

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Editar Despesa");
            editTextTitulo.setText(intent.getStringExtra(EXTRA_TITULO));
            editTextDescricao.setText(intent.getStringExtra(EXTRA_DESCRICAO));
            editTextTipo.setText(intent.getStringExtra(EXTRA_TIPO));
            editTextPreco.setText(intent.getStringExtra(EXTRA_PRECO));
        } else {
            setTitle("Cadastrar Despesa");
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
        menuInflater.inflate(R.menu.add_despesa_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_despesa:
                saveDespesa();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
