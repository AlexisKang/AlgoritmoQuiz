package br.edu.bsi.projetoquizalgoritmo.Helper;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.bsi.projetoquizalgoritmo.Modelo.Medalhas;
import br.edu.bsi.projetoquizalgoritmo.R;

public class AdapterMedalhas extends RecyclerView.Adapter<AdapterMedalhas.MyViewHolder>{

    private List<Medalhas> listaMedalhas;

    public AdapterMedalhas(List<Medalhas> lista){
        this.listaMedalhas = lista;
    }

    @NonNull
    @Override
    public AdapterMedalhas.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_medalha, parent, false);


        return new AdapterMedalhas.MyViewHolder(itemLista);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterMedalhas.MyViewHolder holder, int position) {
        Medalhas m = listaMedalhas.get(position);

        holder.nome.setText(m.getNome());
        holder.descricao.setText(m.getDescricao());
        holder.imagemMedalha.setImageResource(m.getImagem());
    }

    @Override
    public int getItemCount() {
        return listaMedalhas.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome;
        TextView descricao;
        ImageView imagemMedalha;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtNomeMedalha);
            descricao = itemView.findViewById(R.id.txtDescricao);
            imagemMedalha = itemView.findViewById(R.id.imgMedalha);
        }
    }
}
