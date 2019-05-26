package com.codinginflow.despesas.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codinginflow.despesas.Adapter.UsuarioAdapter;
import com.codinginflow.despesas.Model.Usuario;
import com.codinginflow.despesas.R;
//import com.codinginflow.despesas.ViewModel.UsuarioViewModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.codinginflow.despesas.Activity.AddUsuarioActivity.EXTRA_USU_EMAIL;

public class UsuarioActivity extends AppCompatActivity {

    public static final int EDIT_USUARIO_REQUEST = 2;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private List<Usuario> usuarioList = new ArrayList<Usuario>();
    private UsuarioAdapter usuarioAdapter = new UsuarioAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        inicializarFirebase();
        eventoDatabase();

        setTitle("Usuários");

        usuarioAdapter.setOnItemClickListener(new UsuarioAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Usuario usuario) {
                Intent intent = new Intent(UsuarioActivity.this, AddUsuarioActivity.class);

                intent.putExtra(AddUsuarioActivity.EXTRA_USU_HASH, usuario.getHash());
                intent.putExtra(AddUsuarioActivity.EXTRA_USU_NOME, usuario.getNome());
                intent.putExtra(AddUsuarioActivity.EXTRA_USU_TELEFONE, usuario.getTelefone());
                intent.putExtra(AddUsuarioActivity.EXTRA_USU_SENHA, usuario.getSenha());
                intent.putExtra(EXTRA_USU_EMAIL, usuario.getEmail());
                startActivityForResult(intent, EDIT_USUARIO_REQUEST);
            }
        });

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(UsuarioActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void eventoDatabase(){
        databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarioList.clear();
                for(DataSnapshot objSnapchot : dataSnapshot.getChildren()){
                    Usuario usuario = objSnapchot.getValue(Usuario.class);
                    usuarioList.add(usuario);
                }

                RecyclerView recyclerView = findViewById(R.id.usuario_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(UsuarioActivity.this));
                recyclerView.setHasFixedSize(true);

                usuarioAdapter.submitList(usuarioList);
                recyclerView.setAdapter(usuarioAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDIT_USUARIO_REQUEST && resultCode == RESULT_OK){
            String hash = data.getStringExtra(AddUsuarioActivity.EXTRA_USU_HASH);
            if(hash == null){
                Toast.makeText(this, "Usuário não pode ser atualizado", Toast.LENGTH_SHORT).show();
                return;
            } else {
                if(data.hasExtra(EXTRA_USU_EMAIL)){
                    String nome = data.getStringExtra(AddUsuarioActivity.EXTRA_USU_NOME);
                    String email = data.getStringExtra(EXTRA_USU_EMAIL);
                    String telefone = data.getStringExtra(AddUsuarioActivity.EXTRA_USU_TELEFONE);
                    String senha = data.getStringExtra(AddUsuarioActivity.EXTRA_USU_SENHA);

                    Usuario usuario = new Usuario(nome, email, telefone, senha);
                    usuario.setHash(hash);

                    databaseReference.child("Usuario").child(usuario.getHash()).setValue(usuario);
                    Toast.makeText(this, "Dados atualizados com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Email inexistante", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
