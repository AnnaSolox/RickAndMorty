package com.example.rickandmorty.adaptersRecyclerView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.databinding.ItemPersonajeBinding;

import lombok.NonNull;

public class PersonajeViewHolder extends RecyclerView.ViewHolder {
    final ItemPersonajeBinding binding;

    public PersonajeViewHolder(ItemPersonajeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}