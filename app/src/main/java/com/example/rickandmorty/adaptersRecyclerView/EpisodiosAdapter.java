package com.example.rickandmorty.adaptersRecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.databinding.ItemEpisodioBinding;
import com.example.rickandmorty.models.Episodio;

import java.util.ArrayList;
import java.util.List;

public class EpisodiosAdapter extends RecyclerView.Adapter<EpisodioViewHolder> {
    private List<Episodio> episodios = new ArrayList<>();

    @NonNull
    @Override
    public EpisodioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpisodioViewHolder(ItemEpisodioBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodioViewHolder holder, int position) {
        Episodio episodio = episodios.get(position);

        holder.binding.nombreEpisodio.setText(episodio.getNombre());
        holder.binding.identificadorEpisodio.setText(episodio.getIdentificador());

    }

    @Override
    public int getItemCount() {
        return episodios != null ? episodios.size() : 0;
    }

    public void establecerLista(List<Episodio> episodios){
        this.episodios = episodios;
        notifyDataSetChanged();
    }
}
