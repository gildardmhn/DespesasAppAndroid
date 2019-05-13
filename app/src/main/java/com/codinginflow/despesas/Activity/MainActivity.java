package com.codinginflow.despesas.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codinginflow.despesas.Adapter.DespesaAdapter;
import com.codinginflow.despesas.ViewModel.DespesaViewModel;
import com.codinginflow.despesas.Model.Despesa;
import com.codinginflow.despesas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_DESPESA_REQUEST = 1;
    public static final int EDIT_DESPESA_REQUEST = 2;
    private DespesaViewModel despesaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // startActivity(new Intent(getApplicationContext(), LoginActivity.class));

        FloatingActionButton buttonAddDespesa = findViewById(R.id.button_add_despesa);
        buttonAddDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditDespesaActivity.class);
                startActivityForResult(intent, ADD_DESPESA_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final DespesaAdapter despesaAdapater = new DespesaAdapter();
        recyclerView.setAdapter(despesaAdapater);

        despesaViewModel = ViewModelProviders.of(this).get(DespesaViewModel.class);
        despesaViewModel.getAllDespesas().observe(this, new Observer<List<Despesa>>() {
            @Override
            public void onChanged(List<Despesa> despesas) {
                despesaAdapater.submitList(despesas);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                despesaViewModel.delete(despesaAdapater.getDespesaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Despesa removida", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        despesaAdapater.setOnItemClickListener(new DespesaAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Despesa despesa) {
                Intent intent = new Intent(MainActivity.this, AddEditDespesaActivity.class);
                intent.putExtra(AddEditDespesaActivity.EXTRA_ID, despesa.getId());
                intent.putExtra(AddEditDespesaActivity.EXTRA_TITULO, despesa.getTitulo());
                intent.putExtra(AddEditDespesaActivity.EXTRA_DESCRICAO, despesa.getDescricao());
                intent.putExtra(AddEditDespesaActivity.EXTRA_TIPO, despesa.getTipo());
                intent.putExtra(AddEditDespesaActivity.EXTRA_PRECO, despesa.getPreco());
                startActivityForResult(intent, EDIT_DESPESA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_DESPESA_REQUEST && resultCode == RESULT_OK) {
            String titulo = data.getStringExtra(AddEditDespesaActivity.EXTRA_TITULO);
            String descricao = data.getStringExtra(AddEditDespesaActivity.EXTRA_DESCRICAO);
            String tipo = data.getStringExtra(AddEditDespesaActivity.EXTRA_TIPO);
            Double preco = data.getDoubleExtra(AddEditDespesaActivity.EXTRA_PRECO, 0.0);

            Despesa despesa = new Despesa(titulo, descricao, tipo, preco);
            despesaViewModel.insert(despesa);
            Toast.makeText(this, "Despesa salva com sucesso", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_DESPESA_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(AddEditDespesaActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Despesa não pode ser atualizada", Toast.LENGTH_SHORT).show();
                return;
            }

            String titulo = data.getStringExtra(AddEditDespesaActivity.EXTRA_TITULO);
            String descricao = data.getStringExtra(AddEditDespesaActivity.EXTRA_DESCRICAO);
            String tipo = data.getStringExtra(AddEditDespesaActivity.EXTRA_TIPO);
            Double preco = data.getDoubleExtra(AddEditDespesaActivity.EXTRA_PRECO, 0.0);

            Despesa despesa = new Despesa(titulo, descricao, tipo, preco);
            despesa.setId(id);
            despesaViewModel.update(despesa);

            Toast.makeText(this, "Despesa atualizada", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Despesa não salva", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.delete_all_despesas:
                despesaViewModel.deleteAllDespesas();
                Toast.makeText(this, "Todas as despesas foram removidas", Toast.LENGTH_SHORT).show();
                return true;*/
            case R.id.estabelecimento_menu:
                startActivity(new Intent(getApplicationContext(), EstabelecimentoActivity.class));
            case R.id.sair_menu:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
