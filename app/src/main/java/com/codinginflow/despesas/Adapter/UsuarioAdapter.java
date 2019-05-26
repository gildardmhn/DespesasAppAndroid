package com.codinginflow.despesas.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codinginflow.despesas.Model.Usuario;
import com.codinginflow.despesas.R;

import java.util.ArrayList;
import java.util.List;

public class UsuarioAdapter extends ListAdapter<Usuario,UsuarioAdapter.UsuarioHolder> {

    private onItemClickListener listener;

    private List<Usuario> usuarios = new ArrayList<>();

    public UsuarioAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Usuario> DIFF_CALLBACK = new DiffUtil.ItemCallback<Usuario>() {
        @Override
        public boolean areItemsTheSame(@NonNull Usuario oldItem, @NonNull Usuario newItem) {
            return oldItem.getHash() == newItem.getHash();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Usuario oldItem, @NonNull Usuario newItem) {
            return oldItem.getNome().equals(newItem.getNome()) &&
                    oldItem.getEmail().equals(newItem.getEmail()) &&
                    oldItem.getTelefone().equals(newItem.getTelefone()) &&
                    oldItem.getSenha().equals(newItem.getSenha());
        }
    };

    @NonNull
    @Override
    public UsuarioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.usuario_item, parent, false);
        return new UsuarioHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioHolder holder, int position) {
        Usuario currentUsuario = getItem(position);
        holder.textViewNome.setText(currentUsuario.getNome());
        holder.textViewEmail.setText(currentUsuario.getEmail());
        holder.textViewTelefone.setText(currentUsuario.getTelefone());
    }

    public Usuario getUsuarioAt(int position) {
        return getItem(position);
    }

    class UsuarioHolder extends RecyclerView.ViewHolder{
        private TextView textViewNome;
        private TextView textViewEmail;
        private TextView textViewTelefone;


        public UsuarioHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.text_view_usu_nome);
            textViewEmail = itemView.findViewById(R.id.text_view_usu_email);
            textViewTelefone = itemView.findViewById(R.id.text_view_usu_telefone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Usuario usuario);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
