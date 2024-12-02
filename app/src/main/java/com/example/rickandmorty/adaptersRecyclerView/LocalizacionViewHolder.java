package com.example.rickandmorty.adaptersRecyclerView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.databinding.ItemLocalizacionBinding;
import com.example.rickandmorty.viewmodels.LocalizacionViewModel;

public class LocalizacionViewHolder extends RecyclerView.ViewHolder {
    final ItemLocalizacionBinding binding;

    public LocalizacionViewHolder(ItemLocalizacionBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }
}
