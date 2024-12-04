package com.example.rickandmorty.adaptersRecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.databinding.ItemEpisodioBinding;
import com.example.rickandmorty.models.Episodio;
import com.example.rickandmorty.utils.FiltradoUtilidad;
import com.example.rickandmorty.viewmodels.EpisodioViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para el {@link RecyclerView} que muestra la lista de episodios en la interfaz de usuario.
 * <p>
 * Este adaptador es responsable de manejar los elementos de la lista de episodios, incluyendo su
 * visualización y filtrado. Utiliza un {@link EpisodioViewModel} para gestionar los datos y un
 * {@link NavController} para manejar la navegación entre fragmentos al seleccionar un episodio.
 * </p>
 */
public class EpisodiosAdapter extends RecyclerView.Adapter<EpisodioViewHolder> {
    private List<Episodio> episodios = new ArrayList<>();
    private List<Episodio> episodiosOriginal;
    private final NavController navController;
    private final EpisodioViewModel episodioViewModel;
    private final int actionId;

    /**
     * Constructor del adaptador para inicializar los elementos necesarios como el {@link EpisodioViewModel},
     * el {@link NavController} y el ID de acción para la navegación.
     *
     * @param episodioViewModel El {@link EpisodioViewModel} que gestiona los datos del episodio.
     * @param navController El {@link NavController} que maneja la navegación entre fragmentos.
     * @param actionId El ID de la acción de navegación para el episodio seleccionado.
     */
    public EpisodiosAdapter(EpisodioViewModel episodioViewModel, NavController navController, int actionId){
        this.episodioViewModel = episodioViewModel;
        this.navController = navController;
        this.actionId = actionId;
    }

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

        holder.itemView.setOnClickListener(view -> {
            episodioViewModel.seleccionar(episodio);
            navController.navigate(actionId);
        });

    }

    @Override
    public int getItemCount() {
        return episodios != null ? episodios.size() : 0;
    }

    /**
     * Establece la lista de episodios a mostrar en el adaptador.
     *
     * @param episodios La lista de episodios que se desea mostrar.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void establecerLista(List<Episodio> episodios) {
        this.episodios = episodios;
        this.episodiosOriginal = new ArrayList<>(episodios);
        notifyDataSetChanged();
    }

    /**
     * Filtra la lista de episodios por el nombre.
     *
     * @param filtro El texto de filtro para buscar en el nombre de los episodios.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void filtradoPorNombre(String filtro) {
        episodios = FiltradoUtilidad.filtro(episodiosOriginal, filtro, (item, textoFiltro) -> item.getNombre().toLowerCase().contains(textoFiltro.toLowerCase()));
        notifyDataSetChanged();
    }
}
