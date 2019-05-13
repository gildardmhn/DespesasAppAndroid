package com.codinginflow.despesas.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codinginflow.despesas.Model.Usuario;
import com.codinginflow.despesas.R;

import java.util.ArrayList;
import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioHolder> {

    private List<Usuario> usuarios = new ArrayList<>();

    @NonNull
    @Override
    public UsuarioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.usuario_item, parent, false);
        return new UsuarioHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioHolder holder, int position) {
        Usuario currentUsuario = usuarios.get(position);
        holder.textViewId.setText(currentUsuario.getId());
        holder.textViewNome.setText(currentUsuario.getNome());
        holder.textViewEmail.setText(currentUsuario.getEmail());
        holder.textViewTelefone.setText(currentUsuario.getTelefone());
        holder.textViewSenha.setText(currentUsuario.getSenha());
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public void setUsuarios(List<Usuario> usuarios){
        this.usuarios = usuarios;
        notifyDataSetChanged();
    }

    class UsuarioHolder extends RecyclerView.ViewHolder{
        private TextView textViewId;
        private TextView textViewNome;
        private TextView textViewEmail;
        private TextView textViewTelefone;
        private TextView textViewSenha;


        public UsuarioHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.text_view_usu_id);
            textViewNome = itemView.findViewById(R.id.text_view_usu_nome);
            textViewEmail = itemView.findViewById(R.id.text_view_usu_email);
            textViewTelefone = itemView.findViewById(R.id.text_view_usu_telefone);
            textViewSenha = itemView.findViewById(R.id.text_view_usu_senha);
        }
    }
}
