package com.example.rickandmorty.adaptersRecyclerView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.databinding.ItemPersonajeBinding;
/**
 * ViewHolder para los elementos de personaje en un {@link RecyclerView}.
 * <p>
 * Este ViewHolder mantiene una referencia a las vistas de cada ítem en el {@link RecyclerView}, utilizando
 * el {@link ItemPersonajeBinding} generado por el binding para acceder a las vistas de los elementos de personaje.
 * </p>
 */
public class PersonajeViewHolder extends RecyclerView.ViewHolder {
    final ItemPersonajeBinding binding;

    /**
     * Constructor que inicializa el ViewHolder con el binding del ítem.
     *
     * @param binding El objeto de binding que contiene las vistas asociadas al ítem.
     */
    public PersonajeViewHolder(ItemPersonajeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}