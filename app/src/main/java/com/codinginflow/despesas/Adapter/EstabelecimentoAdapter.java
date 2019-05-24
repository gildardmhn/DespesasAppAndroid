package com.codinginflow.despesas.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codinginflow.despesas.Model.Estabelecimento;
import com.codinginflow.despesas.R;


public class EstabelecimentoAdapter extends ListAdapter<Estabelecimento, EstabelecimentoAdapter.EstabelecimentoHolder> {

    private onItemClickListener listener;

    public EstabelecimentoAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Estabelecimento> DIFF_CALLBACK = new DiffUtil.ItemCallback<Estabelecimento>() {
        @Override
        public boolean areItemsTheSame(@NonNull Estabelecimento oldItem, @NonNull Estabelecimento newItem) {
            return oldItem.getHash() == newItem.getHash();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Estabelecimento oldItem, @NonNull Estabelecimento newItem) {
            return oldItem.getNome().equals(newItem.getNome()) &&
                    oldItem.getEndereco().equals(newItem.getEndereco()) &&
                    oldItem.getTelefone().equals(newItem.getTelefone());
        }
    };

    @NonNull
    @Override
    public EstabelecimentoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.estabelecimento_item, parent, false);
        return new EstabelecimentoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EstabelecimentoHolder holder, int position) {
        Estabelecimento currentEstabelecimento = getItem(position);
        holder.textViewNome.setText(currentEstabelecimento.getNome());
        holder.textViewEndereco.setText(currentEstabelecimento.getEndereco());
        holder.textViewTelefone.setText(currentEstabelecimento.getTelefone());

    }


    public Estabelecimento getEstabelecimentoAt(int position) {
        return getItem(position);
    }

    class EstabelecimentoHolder extends RecyclerView.ViewHolder {
        private TextView textViewNome;
        private TextView textViewEndereco;
        private TextView textViewTelefone;

        public EstabelecimentoHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.text_view_est_nome);
            textViewEndereco = itemView.findViewById(R.id.text_view_est_endereco);
            textViewTelefone = itemView.findViewById(R.id.text_view_est_telefone);

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
        void onItemClick(Estabelecimento estabelecimento);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
