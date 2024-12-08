package com.example.rickandmorty.adaptersRecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.R;
import com.example.rickandmorty.databinding.ItemEpisodioLocalizacionBinding;
import com.example.rickandmorty.models.Localizacion;
import com.example.rickandmorty.utils.FiltradoUtilidad;
import com.example.rickandmorty.viewmodels.LocalizacionViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para manejar la lista de localizaciones en un {@link RecyclerView}.
 * <p>
 * Esta clase adapta los datos de la lista de {@link Localizacion} para ser mostrados en un
 * {@link RecyclerView}, permitiendo la visualización de las localizaciones y el filtrado de
 * las mismas por nombre o tipo.
 * </p>
 */
public class LocalizacionesAdapter extends RecyclerView.Adapter<EpisodioLocalizacionViewHolder> {
    private List<Localizacion> localizaciones = new ArrayList<>();
    private List<Localizacion> localizacionesOriginal;
    private final NavController navController;
    private final LocalizacionViewModel localizacionViewModel;

    /**
     * Constructor para crear una instancia del adaptador de localizaciones.
     *
     * @param localizacionViewModel El ViewModel asociado a las localizaciones.
     * @param navController El controlador de navegación para manejar las acciones de navegación.
     */
    public LocalizacionesAdapter(LocalizacionViewModel localizacionViewModel, NavController navController){
        this.localizacionViewModel = localizacionViewModel;
        this.navController = navController;
    }

    @NonNull
    @Override
    public EpisodioLocalizacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpisodioLocalizacionViewHolder(ItemEpisodioLocalizacionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodioLocalizacionViewHolder holder, int position) {
        Localizacion localizacion = localizaciones.get(position);

        holder.binding.nombreItem.setText(localizacion.getNombre());
        holder.binding.infoAdicionalItem.setText(localizacion.getTipo());

        holder.itemView.setOnClickListener(view -> {
            localizacionViewModel.seleccionar(localizacion);
            navController.navigate(R.id.action_localizacionesFragment_to_mostrarLocalizacionFragment);
        });
    }

    @Override
    public int getItemCount() {
        return localizaciones != null ? localizaciones.size() : 0;
    }

    /**
     * Establece la lista de localizaciones a mostrar.
     *
     * @param localizaciones Lista de localizaciones a mostrar.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void establecerLista(List<Localizacion> localizaciones){
        this.localizaciones = localizaciones;
        this.localizacionesOriginal = new ArrayList<>(localizaciones);
        notifyDataSetChanged();
    }

    /**
     * Filtra la lista de localizaciones por nombre.
     *
     * @param filtro Texto a filtrar.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void filtradoPorNombre(String filtro) {
        localizaciones = FiltradoUtilidad.filtro(localizacionesOriginal, filtro, (item, textoFiltro) -> item.getNombre().toLowerCase().contains(textoFiltro.toLowerCase()));
        notifyDataSetChanged();
    }
}
