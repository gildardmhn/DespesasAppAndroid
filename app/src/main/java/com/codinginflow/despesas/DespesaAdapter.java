package com.codinginflow.despesas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DespesaAdapter extends ListAdapter<Despesa, DespesaAdapter.DespesaHolder> {

    private onItemClickListener listener;

    public DespesaAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Despesa> DIFF_CALLBACK = new DiffUtil.ItemCallback<Despesa>() {
        @Override
        public boolean areItemsTheSame(@NonNull Despesa oldItem, @NonNull Despesa newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Despesa oldItem, @NonNull Despesa newItem) {
            return oldItem.getTitulo().equals(newItem.getTitulo()) &&
                    oldItem.getDescricao().equals(newItem.getDescricao()) &&
                    oldItem.getTipo().equals(newItem.getTipo()) &&
                    oldItem.getPreco().equals(newItem.getPreco());
        }
    };

    @NonNull
    @Override
    public DespesaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.despesa_item, parent, false);
        return new DespesaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DespesaHolder holder, int position) {

        Despesa currentDespesa = getItem(position);
        holder.textViewTitulo.setText(currentDespesa.getTitulo());
        holder.textViewDescricao.setText(currentDespesa.getDescricao());
        holder.textViewPreco.setText(String.valueOf(currentDespesa.getPreco() + " R$"));
        holder.textViewTipo.setText(currentDespesa.getTipo());
    }

    public Despesa getDespesaAt(int position) {
        return getItem(position);
    }

    class DespesaHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitulo;
        private TextView textViewPreco;
        private TextView textViewDescricao;
        private TextView textViewTipo;

        public DespesaHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.text_view_titulo);
            textViewPreco = itemView.findViewById(R.id.text_view_preco);
            textViewDescricao = itemView.findViewById(R.id.text_view_descricao);
            textViewTipo = itemView.findViewById(R.id.text_view_tipo);

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
        void onItemClick(Despesa despesa);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
