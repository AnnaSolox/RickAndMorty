package com.example.rickandmorty.utils;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.adaptersRecyclerView.EpisodiosAdapter;
import com.example.rickandmorty.viewmodels.EpisodioViewModel;

/**
 * Clase que encapsula la configuración y la lógica de un RecyclerView para mostrar episodios,
 * junto con la funcionalidad de filtrado por nombre y temporada de los episodios.
 */
public class RecyclerViewEpisodios {
    private final RecyclerView recyclerView;
    private final EpisodiosAdapter episodiosAdapter;
    private final EpisodioViewModel episodioViewModel;

    /**
     * Constructor de la clase {@link RecyclerViewEpisodios}.
     *
     * @param recyclerView El RecyclerView donde se mostrarán los episodios.
     * @param episodioViewModel El ViewModel asociado que gestiona los episodios.
     * @param episodiosAdapter El adaptador que proporciona los datos para el RecyclerView.
     */
    public RecyclerViewEpisodios(RecyclerView recyclerView, EpisodioViewModel episodioViewModel, EpisodiosAdapter episodiosAdapter){
        this.recyclerView = recyclerView;
        this.episodioViewModel = episodioViewModel;
        this.episodiosAdapter = episodiosAdapter;
    }

    /**
     * Configura el RecyclerView con el adaptador y el LayoutManager.
     *
     * @param context El contexto en el que se está configurando el RecyclerView.
     */
    public void setupRecyclerView(Context context){
        recyclerView.setAdapter(episodiosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    /**
     * Observa los datos de los episodios desde el ViewModel y actualiza el adaptador
     * con los episodios obtenidos.
     *
     * @param lifecycleOwner El propietario del ciclo de vida que observará los datos.
     */
    public void observarEpisodios(LifecycleOwner lifecycleOwner){
        episodioViewModel.getEpisodiosLiveData().observe(lifecycleOwner, episodios -> {
            Log.d("EpisodiosRecycler", "Tamaño de la lista de episodios: " + episodios.size());
            episodiosAdapter.establecerLista(episodios);
        });
    }

    /**
     * Configura la funcionalidad de búsqueda en la barra de búsqueda del RecyclerView.
     *
     * @param searchBar La barra de búsqueda {@link SearchView} que se utilizará para filtrar los episodios.
     */
    public void configurarBusqueda(SearchView searchBar) {
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String busqueda) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String busqueda) {
                episodiosAdapter.filtradoPorNombre(busqueda);
                return true;
            }
        });
    }
}
