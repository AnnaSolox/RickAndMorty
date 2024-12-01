package com.example.rickandmorty.adaptersRecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.databinding.ItemLocalizacionBinding;
import com.example.rickandmorty.models.Localizacion;

import java.util.ArrayList;
import java.util.List;

public class LocalizacionesAdapter extends RecyclerView.Adapter<LocalizacionViewHolder> {
    private List<Localizacion> localizaciones = new ArrayList<>();

    @NonNull
    @Override
    public LocalizacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocalizacionViewHolder(ItemLocalizacionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocalizacionViewHolder holder, int position) {
        Localizacion localizacion = localizaciones.get(position);

        holder.binding.nombreLocalizacion.setText(localizacion.getNombre());
        holder.binding.tipoLocalizacion.setText(localizacion.getTipo());
    }

    @Override
    public int getItemCount() {
        return localizaciones != null ? localizaciones.size() : 0;
    }

    public void establecerLista(List<Localizacion> localizaciones){
        this.localizaciones = localizaciones;
        notifyDataSetChanged();
    }
}
