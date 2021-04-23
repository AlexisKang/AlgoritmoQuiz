package br.edu.bsi.projetoquizalgoritmo.Helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.bsi.projetoquizalgoritmo.Modelo.Usuario;
import br.edu.bsi.projetoquizalgoritmo.R;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Usuario> listaUsuario;

    public Adapter(List<Usuario> lista){
        this.listaUsuario = lista;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome;
        TextView ranking;
        TextView pontuacao;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtNomeMedalha);
            ranking = itemView.findViewById(R.id.txtRanking);
            pontuacao = itemView.findViewById(R.id.txtDescricao);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Usuario usuario = listaUsuario.get(position);

        holder.nome.setText(usuario.getNome());
        holder.pontuacao.setText("Pontuação: "+ String.valueOf(usuario.getPontos().getSobrevivencia()));
        holder.ranking.setText("Ranking: "+ String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return listaUsuario.size();
    }
}
