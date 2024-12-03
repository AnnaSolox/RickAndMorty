package com.example.rickandmorty.utils;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.adaptersRecyclerView.LocalizacionesAdapter;
import com.example.rickandmorty.viewmodels.LocalizacionViewModel;

/**
 * Clase que encapsula la configuración y la lógica de un RecyclerView para mostrar localizaciones,
 * junto con la funcionalidad de filtrado por nombre y tipo de las localizaciones.
 */
public class RecyclerViewLocalizaciones {
    private final RecyclerView recyclerView;
    private final LocalizacionesAdapter localizacionesAdapter;
    private final LocalizacionViewModel localizacionViewModel;

    /**
     * Constructor de la clase {@link RecyclerViewLocalizaciones}.
     *
     * @param recyclerView El RecyclerView donde se mostrarán las localizaciones.
     * @param localizacionViewModel El ViewModel asociado que gestiona las localizaciones.
     * @param localizacionesAdapter El adaptador que proporciona los datos para el RecyclerView.
     */
    public RecyclerViewLocalizaciones(RecyclerView recyclerView, LocalizacionViewModel localizacionViewModel, LocalizacionesAdapter localizacionesAdapter){
        this.recyclerView = recyclerView;
        this.localizacionViewModel = localizacionViewModel;
        this.localizacionesAdapter = localizacionesAdapter;
    }

    /**
     * Configura el RecyclerView con el adaptador y el LayoutManager.
     *
     * @param context El contexto en el que se está configurando el RecyclerView.
     */
    public void setupRecyclerView(Context context){
        recyclerView.setAdapter(localizacionesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    /**
     * Observa los datos de las localizaciones desde el ViewModel y actualiza el adaptador
     * con las localizaciones obtenidas.
     *
     * @param lifecycleOwner El propietario del ciclo de vida que observará los datos.
     */
    public void observarLocalizaciones(LifecycleOwner lifecycleOwner){
        localizacionViewModel.getLocalizacionLiveData().observe(lifecycleOwner, localizacions -> {
            Log.d("RecyclerLocalizaciones", "Tamaño de la lista de localizaciones: " + localizacions.size());
            localizacionesAdapter.establecerLista(localizacions);
        });
    }

    /**
     * Configura la funcionalidad de búsqueda en la barra de búsqueda del RecyclerView.
     *
     * @param searchBar La barra de búsqueda {@link SearchView} que se utilizará para filtrar las localizaciones.
     */
    public void configurarBusqueda(SearchView searchBar) {
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String busqueda) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String busqueda) {
                localizacionesAdapter.filtradoPorNombre(busqueda);
                localizacionesAdapter.filtradoPorTipo(busqueda);
                return true;
            }
        });
    }

}
