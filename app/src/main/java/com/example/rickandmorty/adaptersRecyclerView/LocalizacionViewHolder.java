package com.example.rickandmorty.adaptersRecyclerView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.databinding.ItemLocalizacionBinding;

/**
 * ViewHolder para un ítem de localización en el {@link RecyclerView}.
 * <p>
 * Esta clase es responsable de mantener las vistas de cada ítem en el {@link RecyclerView}
 * que representa una localización. El ViewHolder utiliza {@link ItemLocalizacionBinding}
 * para enlazar las vistas del ítem con los datos correspondientes.
 * </p>
 */
public class LocalizacionViewHolder extends RecyclerView.ViewHolder {
    final ItemLocalizacionBinding binding;

    /**
     * Constructor para crear una instancia de {@link LocalizacionViewHolder}.
     *
     * @param binding El enlace a las vistas del ítem de localización.
     */
    public LocalizacionViewHolder(ItemLocalizacionBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }
}
