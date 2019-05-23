package com.codinginflow.despesas.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_DESPESA_REQUEST = 1;
    public static final int EDIT_DESPESA_REQUEST = 2;
    private DespesaViewModel despesaViewModel;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarFirebase();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // startActivity(new Intent(getApplicationContext(), LoginActivity.class));

        FloatingActionButton buttonAddDespesa = findViewById(R.id.button_add_despesa);
        buttonAddDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditDespesaActivity.class);
                startActivityForResult(intent, ADD_DESPESA_REQUEST);
            }
        });

        FloatingActionButton buttonMapa = findViewById(R.id.button_map);
        buttonMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
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

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_DESPESA_REQUEST && resultCode == RESULT_OK) {
            String titulo = data.getStringExtra(AddEditDespesaActivity.EXTRA_TITULO);
            String descricao = data.getStringExtra(AddEditDespesaActivity.EXTRA_DESCRICAO);
            String tipo = data.getStringExtra(AddEditDespesaActivity.EXTRA_TIPO);
            Double preco = data.getDoubleExtra(AddEditDespesaActivity.EXTRA_PRECO, 0.0);
            String hash = UUID.randomUUID().toString();

            Despesa despesa = new Despesa(titulo, descricao, tipo, preco);

            despesa.setHash(hash);
            databaseReference.child("Despesa").child(despesa.getHash()).setValue(despesa);
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
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            /*case R.id.delete_all_despesas:
                despesaViewModel.deleteAllDespesas();
                Toast.makeText(this, "Todas as despesas foram removidas", Toast.LENGTH_SHORT).show();
                return true;*/
            case R.id.estabelecimento_menu:
                startActivity(new Intent(getApplicationContext(), EstabelecimentoActivity.class));
                return true;
            case R.id.sair_menu:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            case R.id.usuario_menu:
                startActivity(new Intent(getApplicationContext(), UsuarioActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
