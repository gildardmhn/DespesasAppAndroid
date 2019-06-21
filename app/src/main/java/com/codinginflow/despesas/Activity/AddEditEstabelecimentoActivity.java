package com.codinginflow.despesas.Activity;

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

import com.codinginflow.despesas.R;

public class AddEditEstabelecimentoActivity extends AppCompatActivity {

    public static final String EXTRA_HASH_EST = "com.codinginflow.despesas.EXTRA_HASH_EST";
    public static final String EXTRA_NOME = "com.codinginflow.despesas.EXTRA_NOME";
    public static final String EXTRA_ENDERECO = "com.codinginflow.despesas.EXTRA_ENDERECO";
    public static final String EXTRA_TELEFONE = "com.codinginflow.despesas.EXTRA_TELEFONE";
    public static final int OPEN_MAP = 1;

    private EditText editTextNome;
    private EditText editTextEndereco;
    private EditText editTextTelefone;
    private Button buttonVerMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_estabelecimento);

        editTextNome = findViewById(R.id.edit_text_est_nome);
        editTextEndereco = findViewById(R.id.edit_text_est_endereco);
        editTextTelefone = findViewById(R.id.edit_text_est_telefone);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        buttonVerMapa = findViewById(R.id.ver_mapa);
        buttonVerMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEditEstabelecimentoActivity.this, MapsActivity.class);
                String endereco = editTextEndereco.getText().toString();
                intent.putExtra(MapsActivity.EXTRA_MAPA_ENDERECO, endereco);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_HASH_EST)){
            setTitle("Editar Estabelecimento");
            editTextNome.setText(intent.getStringExtra(EXTRA_NOME));
            editTextEndereco.setText(intent.getStringExtra(EXTRA_ENDERECO));
            editTextTelefone.setText(intent.getStringExtra(EXTRA_TELEFONE));
        } else {
            setTitle("Cadastrar Estabelecimento");
            buttonVerMapa.setVisibility(View.GONE);
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

        String hash = getIntent().getStringExtra(EXTRA_HASH_EST);
        if(hash != null){
            data.putExtra(EXTRA_HASH_EST, hash);
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
            case R.id.delete_estabelecimento:
                deleteEstabelecimento();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void deleteEstabelecimento() {
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_HASH_EST)){
            String hash = intent.getStringExtra(EXTRA_HASH_EST);
            Intent deleteHash = new Intent();
            deleteHash.putExtra(EXTRA_HASH_EST, hash);
            setResult(RESULT_OK, deleteHash);
            finish();
        }else {
            Toast.makeText(this, "Não pode remover um estabelecimento inexistante", Toast.LENGTH_SHORT).show();
        }
    }

}
