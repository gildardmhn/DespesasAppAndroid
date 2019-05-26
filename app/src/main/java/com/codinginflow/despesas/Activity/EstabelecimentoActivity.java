package com.codinginflow.despesas.Activity;

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

import com.codinginflow.despesas.Adapter.DespesaAdapter;
import com.codinginflow.despesas.Model.Despesa;
import com.codinginflow.despesas.Model.Estabelecimento;
import com.codinginflow.despesas.Adapter.EstabelecimentoAdapter;
//import com.codinginflow.despesas.ViewModel.EstabelecimentoViewModel;
import com.codinginflow.despesas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.codinginflow.despesas.Activity.AddEditEstabelecimentoActivity.EXTRA_NOME;

public class EstabelecimentoActivity extends AppCompatActivity {
    public static final int ADD_ESTABELECIMENTO_REQUEST = 1;
    public static final int EDIT_ESTABELECIMENTO_REQUEST = 2;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private List<Estabelecimento> estabelecimentoList = new ArrayList<Estabelecimento>();
    private EstabelecimentoAdapter estabelecimentoAdapter = new EstabelecimentoAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estabelecimento);

        inicializarFirebase();
        eventoDatabase();

        setTitle("Estabelecimentos");

        FloatingActionButton buttonAddDespesa = findViewById(R.id.button_add_estabelecimento);
        buttonAddDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EstabelecimentoActivity.this, AddEditEstabelecimentoActivity.class);
                startActivityForResult(intent, ADD_ESTABELECIMENTO_REQUEST);
            }
        });

        estabelecimentoAdapter.setOnItemClickListener(new EstabelecimentoAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Estabelecimento estabelecimento) {
                Intent intent = new Intent(EstabelecimentoActivity.this, AddEditEstabelecimentoActivity.class);

                intent.putExtra(AddEditEstabelecimentoActivity.EXTRA_HASH_EST, estabelecimento.getHash());
                intent.putExtra(AddEditEstabelecimentoActivity.EXTRA_NOME, estabelecimento.getNome());
                intent.putExtra(AddEditEstabelecimentoActivity.EXTRA_ENDERECO, estabelecimento.getEndereco());
                intent.putExtra(AddEditEstabelecimentoActivity.EXTRA_TELEFONE, estabelecimento.getTelefone());
                startActivityForResult(intent, EDIT_ESTABELECIMENTO_REQUEST);
            }
        });
    }

    private void eventoDatabase() {
        databaseReference.child("Estabelecimento").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                estabelecimentoList.clear();
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Estabelecimento estabelecimento = objSnapshot.getValue(Estabelecimento.class);
                    estabelecimentoList.add(estabelecimento);
                }

                RecyclerView recyclerView = findViewById(R.id.estabelecimento_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(EstabelecimentoActivity.this));
                recyclerView.setHasFixedSize(true);

                estabelecimentoAdapter.submitList(estabelecimentoList);
                recyclerView.setAdapter(estabelecimentoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(EstabelecimentoActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ESTABELECIMENTO_REQUEST && resultCode == RESULT_OK) {
            String nome = data.getStringExtra(EXTRA_NOME);
            String endereco = data.getStringExtra(AddEditEstabelecimentoActivity.EXTRA_ENDERECO);
            String telefone = data.getStringExtra(AddEditEstabelecimentoActivity.EXTRA_TELEFONE);
            String hash = UUID.randomUUID().toString();

            Estabelecimento estabelecimento = new Estabelecimento(nome, endereco, telefone);
            estabelecimento.setHash(hash);
            databaseReference.child("Estabelecimento").child(estabelecimento.getHash()).setValue(estabelecimento);
            Toast.makeText(this, "Estabelecimento salvo com sucesso", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_ESTABELECIMENTO_REQUEST && resultCode == RESULT_OK) {
            String hash = data.getStringExtra(AddEditEstabelecimentoActivity.EXTRA_HASH_EST);

            if (hash == null) {
                Toast.makeText(this, "Estabelecimento não pode ser atualizado", Toast.LENGTH_SHORT).show();
                return;
            } else {
                if(data.hasExtra(EXTRA_NOME)){
                    String nome = data.getStringExtra(AddEditEstabelecimentoActivity.EXTRA_NOME);
                    String endereco = data.getStringExtra(AddEditEstabelecimentoActivity.EXTRA_ENDERECO);
                    String telefone = data.getStringExtra(AddEditEstabelecimentoActivity.EXTRA_TELEFONE);

                    Estabelecimento estabelecimento = new Estabelecimento(nome, endereco, telefone);
                    estabelecimento.setHash(hash);
                    databaseReference.child("Estabelecimento").child(estabelecimento.getHash()).setValue(estabelecimento);

                    Toast.makeText(this, "Estabelecimento atualizado", Toast.LENGTH_SHORT).show();
                } else {
                    Estabelecimento estabelecimento = new Estabelecimento();
                    estabelecimento.setHash(hash);
                    databaseReference.child("Estabelecimento").child(estabelecimento.getHash()).removeValue();
                }
            }


        } else {
            Toast.makeText(this, "Estabelecimento não salvo", Toast.LENGTH_SHORT).show();
        }
    }
}
