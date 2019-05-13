package com.codinginflow.despesas.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codinginflow.despesas.Adapter.UsuarioAdapter;
import com.codinginflow.despesas.Model.Usuario;
import com.codinginflow.despesas.R;
import com.codinginflow.despesas.ViewModel.UsuarioViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class UsuarioActivity extends AppCompatActivity {

    public static final int ADD_USUARIO_REQUEST = 1;
    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        setTitle("Lista de Usuários");

//        FloatingActionButton buttonAddDespesa = findViewById(R.id.button_add_usuario);
//        buttonAddDespesa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UsuarioActivity.this, CadastroActivity.class);
//                startActivityForResult(intent, ADD_USUARIO_REQUEST);
//            }
//        });

        RecyclerView recyclerView = findViewById(R.id.usuario_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final UsuarioAdapter usuarioAdapter = new UsuarioAdapter();
        recyclerView.setAdapter(usuarioAdapter);

        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel.class);
        usuarioViewModel.getAllUsuarios().observe(this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                usuarioAdapter.setUsuarios(usuarios);
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == ADD_USUARIO_REQUEST && resultCode == RESULT_OK){
//            String nome = data.getStringExtra(CadastroActivity.EXTRA_USU_NOME);
//            String email = data.getStringExtra(CadastroActivity.EXTRA_EMAIL);
//            String telefone = data.getStringExtra(CadastroActivity.EXTRA_USU_TELEFONE);
//            String senha = data.getStringExtra(CadastroActivity.EXTRA_SENHA);
//
//            Usuario usuario = new Usuario(nome, email, telefone, senha);
//            usuarioViewModel.insert(usuario);
//
//            Toast.makeText(this, "Usuario salvo", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Usuario não salvo", Toast.LENGTH_SHORT).show();
//        }
//    }
}
