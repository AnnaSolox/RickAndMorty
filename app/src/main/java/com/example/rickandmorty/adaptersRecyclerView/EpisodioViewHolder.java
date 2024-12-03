package com.example.rickandmorty.adaptersRecyclerView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.databinding.ItemEpisodioBinding;

/**
 * ViewHolder que mantiene las vistas de un ítem en el {@link RecyclerView} para un episodio.
 * <p>
 * Esta clase es responsable de contener las vistas que componen un ítem individual en la lista
 * de episodios, y permite acceder a ellas para actualizar su contenido.
 * </p>
 */
public class EpisodioViewHolder extends RecyclerView.ViewHolder {
    final ItemEpisodioBinding binding;

    /**
     * Constructor que recibe el enlace a las vistas del ítem de episodio.
     *
     * @param binding El objeto {@link ItemEpisodioBinding} que contiene las vistas del ítem de episodio.
     */
    public EpisodioViewHolder(ItemEpisodioBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }
}
