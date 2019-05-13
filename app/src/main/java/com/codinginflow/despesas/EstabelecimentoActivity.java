package com.codinginflow.despesas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class EstabelecimentoActivity extends AppCompatActivity {
    public static final int ADD_ESTABELECIMENTO_REQUEST = 1;
    public static final int EDIT_ESTABELECIMENTO_REQUEST = 2;

    private EstabelecimentoViewModel estabelecimentoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estabelecimento);

        setTitle("Estabelecimentos");

        FloatingActionButton buttonAddDespesa = findViewById(R.id.button_add_estabelecimento);
        buttonAddDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EstabelecimentoActivity.this, AddEditEstabelecimentoActivity.class);
                startActivityForResult(intent, ADD_ESTABELECIMENTO_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.estabelecimento_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final EstabelecimentoAdapter estabelecimentoAdapter = new EstabelecimentoAdapter();
        recyclerView.setAdapter(estabelecimentoAdapter);

        estabelecimentoViewModel = ViewModelProviders.of(this).get(EstabelecimentoViewModel.class);
        estabelecimentoViewModel.getAllEstabelecimentos().observe(this, new Observer<List<Estabelecimento>>() {
            @Override
            public void onChanged(List<Estabelecimento> estabelecimentos) {
                estabelecimentoAdapter.submitList(estabelecimentos);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                estabelecimentoViewModel.delete(estabelecimentoAdapter.getEstabelecimentoAt(viewHolder.getAdapterPosition()));
                Toast.makeText(EstabelecimentoActivity.this, "Estabelecimento removido", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        estabelecimentoAdapter.setOnItemClickListener(new EstabelecimentoAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Estabelecimento estabelecimento) {
                Intent intent = new Intent(EstabelecimentoActivity.this, AddEditEstabelecimentoActivity.class);

                intent.putExtra(AddEditEstabelecimentoActivity.EXTRA_ID, estabelecimento.getId());
                intent.putExtra(AddEditEstabelecimentoActivity.EXTRA_NOME, estabelecimento.getNome());
                intent.putExtra(AddEditEstabelecimentoActivity.EXTRA_ENDERECO, estabelecimento.getEndereco());
                intent.putExtra(AddEditEstabelecimentoActivity.EXTRA_TELEFONE, estabelecimento.getTelefone());
                startActivityForResult(intent, EDIT_ESTABELECIMENTO_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ESTABELECIMENTO_REQUEST && resultCode == RESULT_OK) {
            String nome = data.getStringExtra(AddEditEstabelecimentoActivity.EXTRA_NOME);
            String endereco = data.getStringExtra(AddEditEstabelecimentoActivity.EXTRA_ENDERECO);
            String telefone = data.getStringExtra(AddEditEstabelecimentoActivity.EXTRA_TELEFONE);

            Estabelecimento estabelecimento = new Estabelecimento(nome, endereco, telefone);
            estabelecimentoViewModel.insert(estabelecimento);
            Toast.makeText(this, "Estabelecimento salva com sucesso", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_ESTABELECIMENTO_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditEstabelecimentoActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Estabelecimento não pode ser atualizado", Toast.LENGTH_SHORT).show();
                return;
            }

            String nome = data.getStringExtra(AddEditEstabelecimentoActivity.EXTRA_NOME);
            String endereco = data.getStringExtra(AddEditEstabelecimentoActivity.EXTRA_ENDERECO);
            String telefone = data.getStringExtra(AddEditEstabelecimentoActivity.EXTRA_TELEFONE);

            Estabelecimento estabelecimento = new Estabelecimento(nome, endereco, telefone);
            estabelecimento.setId(id);
            estabelecimentoViewModel.update(estabelecimento);

            Toast.makeText(this, "Estabelecimento atualizado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Estabelecimento não salvo", Toast.LENGTH_SHORT).show();
        }
    }
}
