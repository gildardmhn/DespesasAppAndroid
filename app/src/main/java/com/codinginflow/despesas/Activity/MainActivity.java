package com.codinginflow.despesas.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codinginflow.despesas.Adapter.DespesaAdapter;
import com.codinginflow.despesas.Conexao.Conexao;
import com.codinginflow.despesas.Model.Despesa;
import com.codinginflow.despesas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.codinginflow.despesas.Activity.AddEditDespesaActivity.EXTRA_HASH;
import static com.codinginflow.despesas.Activity.AddEditDespesaActivity.EXTRA_TITULO;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int ADD_DESPESA_REQUEST = 1;
    public static final int EDIT_DESPESA_REQUEST = 2;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private List<Despesa> despesaList = new ArrayList<Despesa>();
    private DespesaAdapter despesaAdapater = new DespesaAdapter();

    private FirebaseAuth auth;
    private FirebaseUser user;

    private TextView textViewHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarFirebase();
        eventoDatabase();

        // Drawe menu
        NavigationView navigationView = findViewById(R.id.drawer_navigation);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton buttonAddDespesa = findViewById(R.id.button_add_despesa);
        buttonAddDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditDespesaActivity.class);
                startActivityForResult(intent, ADD_DESPESA_REQUEST);
            }
        });


        despesaAdapater.setOnItemClickListener(new DespesaAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Despesa despesa) {
                Intent intent = new Intent(MainActivity.this, AddEditDespesaActivity.class);
                intent.putExtra(AddEditDespesaActivity.EXTRA_HASH, despesa.getHash());
                intent.putExtra(EXTRA_TITULO, despesa.getTitulo());
                intent.putExtra(AddEditDespesaActivity.EXTRA_DESCRICAO, despesa.getDescricao());
                intent.putExtra(AddEditDespesaActivity.EXTRA_TIPO, despesa.getTipo());
                intent.putExtra(AddEditDespesaActivity.EXTRA_PRECO, despesa.getPreco());
                startActivityForResult(intent, EDIT_DESPESA_REQUEST);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.despesa_menu:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.estabelecimento_menu:
                startActivity(new Intent(getApplicationContext(), EstabelecimentoActivity.class));
                break;
            case R.id.usuario_menu:
                startActivity(new Intent(getApplicationContext(), UsuarioActivity.class));
                break;
            case R.id.sair_menu:
                eventoClick();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void eventoClick() {
        Conexao.logout();
        finish();
        // Ir para login
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();

//        FirebaseUser usuarioAtual = auth.getCurrentUser();
//
//        if (usuarioAtual == null) {
//            Toast.makeText(this, "Seja bem vindo " + usuarioAtual.getEmail(), Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Usuário não logado!", Toast.LENGTH_SHORT).show();
//        }

        verificaUsuario();
    }

    private void verificaUsuario() {
        if (user == null) {
            finish();
        } else {
            //  Toast.makeText(this, "Usuário: " + user.getEmail(), Toast.LENGTH_SHORT).show();
            System.out.println(user.getEmail());
        }
    }

    private void eventoDatabase() {
        databaseReference.child("Despesa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                despesaList.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Despesa despesa = objSnapshot.getValue(Despesa.class);
                    despesaList.add(despesa);
                }

                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setHasFixedSize(true);

                despesaAdapater.submitList(despesaList);
                recyclerView.setAdapter(despesaAdapater);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
            String titulo = data.getStringExtra(EXTRA_TITULO);
            String descricao = data.getStringExtra(AddEditDespesaActivity.EXTRA_DESCRICAO);
            String tipo = data.getStringExtra(AddEditDespesaActivity.EXTRA_TIPO);
            Double preco = data.getDoubleExtra(AddEditDespesaActivity.EXTRA_PRECO, 0.0);
            String hash = UUID.randomUUID().toString();

            Despesa despesa = new Despesa(titulo, descricao, tipo, preco);

            despesa.setHash(hash);
            databaseReference.child("Despesa").child(despesa.getHash()).setValue(despesa);
//            despesaViewModel.insert(despesa);


            Toast.makeText(this, "Despesa salva com sucesso", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_DESPESA_REQUEST && resultCode == RESULT_OK) {

            String hash = data.getStringExtra(AddEditDespesaActivity.EXTRA_HASH);
            if (hash == null) {
                Toast.makeText(this, "Despesa não pode ser atualizada", Toast.LENGTH_SHORT).show();
                return;
            } else {
                if (data.hasExtra(EXTRA_TITULO)) {
                    String titulo = data.getStringExtra(EXTRA_TITULO);
                    String descricao = data.getStringExtra(AddEditDespesaActivity.EXTRA_DESCRICAO);
                    String tipo = data.getStringExtra(AddEditDespesaActivity.EXTRA_TIPO);
                    Double preco = data.getDoubleExtra(AddEditDespesaActivity.EXTRA_PRECO, 0.0);

                    Despesa despesa = new Despesa(titulo, descricao, tipo, preco);
                    despesa.setHash(hash);
                    databaseReference.child("Despesa").child(despesa.getHash()).setValue(despesa);
                    Toast.makeText(this, "Despesa atualizada", Toast.LENGTH_SHORT).show();
                } else {
                    Despesa despesa = new Despesa();
                    despesa.setHash(hash);
                    databaseReference.child("Despesa").child(despesa.getHash()).removeValue();
                }

            }

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
            case R.id.despesa_menu:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            case R.id.estabelecimento_menu:
                startActivity(new Intent(getApplicationContext(), EstabelecimentoActivity.class));
                return true;
            case R.id.usuario_menu:
                startActivity(new Intent(getApplicationContext(), UsuarioActivity.class));
                return true;
            case R.id.sair_menu:
                eventoClick();
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
