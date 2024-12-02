package com.example.rickandmorty.adaptersRecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.R;
import com.example.rickandmorty.databinding.ItemLocalizacionBinding;
import com.example.rickandmorty.models.Localizacion;
import com.example.rickandmorty.utils.FiltradoUtilidad;
import com.example.rickandmorty.viewmodels.LocalizacionViewModel;

import java.util.ArrayList;
import java.util.List;

public class LocalizacionesAdapter extends RecyclerView.Adapter<LocalizacionViewHolder> {
    private List<Localizacion> localizaciones = new ArrayList<>();
    private List<Localizacion> localizacionesOriginal;
    private final NavController navController;
    private final LocalizacionViewModel localizacionViewModel;

    public LocalizacionesAdapter(LocalizacionViewModel localizacionViewModel, NavController navController){
        this.localizacionViewModel = localizacionViewModel;
        this.navController = navController;
    }

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

        holder.itemView.setOnClickListener(view -> {
            localizacionViewModel.seleccionar(localizacion);
            navController.navigate(R.id.action_localizacionesFragment_to_mostrarLocalizacionFragment);
        });
    }

    @Override
    public int getItemCount() {
        return localizaciones != null ? localizaciones.size() : 0;
    }

    public void establecerLista(List<Localizacion> localizaciones){
        this.localizaciones = localizaciones;
        this.localizacionesOriginal = new ArrayList<>(localizaciones);
        notifyDataSetChanged();
    }

    public void filtradoPorNombre(String filtro) {
        localizaciones = FiltradoUtilidad.filtro(localizacionesOriginal, filtro, (item, textoFiltro) -> item.getNombre().toLowerCase().contains(textoFiltro.toLowerCase()));
        notifyDataSetChanged();
    }

    public void filtradoPorTipo(String filtro){
        localizaciones = FiltradoUtilidad.filtro(localizacionesOriginal, filtro, (item, textoFiltro) -> item.getTipo().toLowerCase().contains(textoFiltro.toLowerCase()));
        notifyDataSetChanged();
    }


}
