package com.example.rickandmorty.adaptersRecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.databinding.ItemEpisodioBinding;
import com.example.rickandmorty.models.Episodio;
import com.example.rickandmorty.utils.FiltradoUtilidad;

import java.util.ArrayList;
import java.util.List;

public class EpisodiosAdapter extends RecyclerView.Adapter<EpisodioViewHolder> {
    private List<Episodio> episodios = new ArrayList<>();
    private List<Episodio> episodiosOriginal;

    @NonNull
    @Override
    public EpisodioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpisodioViewHolder(ItemEpisodioBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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

    public void establecerLista(List<Episodio> episodios) {
        this.episodios = episodios;
        this.episodiosOriginal = new ArrayList<>(episodios);
        notifyDataSetChanged();
    }

    public void filtradoPorNombre(String filtro) {
        episodios = FiltradoUtilidad.filtro(episodiosOriginal, filtro, (item, textoFiltro) -> item.getNombre().toLowerCase().contains(textoFiltro.toLowerCase()));
        notifyDataSetChanged();
    }

    public void filtradoPorTemporadaEpisodio(String filtro){
        episodios = FiltradoUtilidad.filtro(episodiosOriginal, filtro, (item, textoFiltro) -> item.getIdentificador().toLowerCase().contains(textoFiltro.toLowerCase()));
        notifyDataSetChanged();
    }
}
