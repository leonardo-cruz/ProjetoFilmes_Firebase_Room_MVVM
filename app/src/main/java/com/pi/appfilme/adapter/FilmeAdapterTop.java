package com.pi.appfilme.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pi.appfilme.R;
import com.pi.appfilme.model.filme.BuscaEBreve.ResultFilme;
import com.pi.appfilme.view.FilmeDetalheActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FilmeAdapterTop extends RecyclerView.Adapter<FilmeAdapterTop.ViewHolder> {
    private List<ResultFilme> listTop;

    public FilmeAdapterTop(List<ResultFilme> listPlaying) {
        this.listTop = listPlaying;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filme_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultFilme resultFilme = listTop.get(position);
        holder.onBind(resultFilme);
    }

    @Override
    public int getItemCount() {
        return listTop.size() - 17;
    }

    public void atualizaListaTop(List<ResultFilme> novaLista) {
        if (this.listTop.isEmpty()) {
            this.listTop = novaLista;
        } else {
            this.listTop.addAll(novaLista);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView textTitulo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagemCartazRecycler);
            textTitulo = itemView.findViewById(R.id.textRecyclerCartaz);
            itemView.setOnClickListener(this);
        }

        public void onBind (ResultFilme resultFilme){
            textTitulo.setText(resultFilme.getTitle());
            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+ resultFilme.getPosterPath()).into(imageView);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), FilmeDetalheActivity.class);
            long idFilme = listTop.get(getAdapterPosition()).getId();
            intent.putExtra("Id", idFilme);
            v.getContext().startActivity(intent);
        }
    }
}