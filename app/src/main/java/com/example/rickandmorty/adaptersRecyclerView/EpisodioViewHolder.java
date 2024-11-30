package com.example.rickandmorty.adaptersRecyclerView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.databinding.ItemEpisodioBinding;

public class EpisodioViewHolder extends RecyclerView.ViewHolder {
    final ItemEpisodioBinding binding;

    public EpisodioViewHolder(ItemEpisodioBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }
}
