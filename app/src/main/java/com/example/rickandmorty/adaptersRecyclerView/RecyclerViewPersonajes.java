package com.example.rickandmorty.adaptersRecyclerView;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.viewmodels.PersonajeViewModel;

/**
 * Clase que encapsula la configuración y la lógica de un RecyclerView para mostrar personajes,
 * incluyendo la visualización en un diseño de cuadrícula, la observación de datos de personajes y favoritos,
 * y la funcionalidad de filtrado por nombre.
 */
public class RecyclerViewPersonajes {
    private final RecyclerView recyclerView;
    private final PersonajesAdapter personajesAdapter;
    private final PersonajeViewModel personajeViewModel;

    /**
     * Constructor de la clase {@link RecyclerViewPersonajes}.
     *
     * @param recyclerView El RecyclerView donde se mostrarán los personajes.
     * @param personajeViewModel El ViewModel asociado que gestiona los personajes.
     * @param personajesAdapter El adaptador que proporciona los datos para el RecyclerView.
     */
    public RecyclerViewPersonajes(RecyclerView recyclerView, PersonajeViewModel personajeViewModel, PersonajesAdapter personajesAdapter){
        this.recyclerView = recyclerView;
        this.personajeViewModel = personajeViewModel;
        this.personajesAdapter = personajesAdapter;
    }

    /**
     * Configura el RecyclerView con el adaptador y el LayoutManager, estableciendo un GridLayoutManager
     * para mostrar los personajes en una cuadrícula con dos columnas.
     *
     * @param context El contexto en el que se está configurando el RecyclerView.
     */
    public void setupRecyclerView(Context context, int columns){
        recyclerView.setAdapter(personajesAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context, columns));
    }

    /**
     * Observa los datos de los personajes desde el ViewModel y actualiza el adaptador
     * con los personajes obtenidos.
     *
     * @param lifecycleOwner El propietario del ciclo de vida que observará los datos.
     */
    public void observarPersonajes(LifecycleOwner lifecycleOwner){
        personajeViewModel.getPersonajeLiveData().observe(lifecycleOwner, personajes -> {
            Log.d("PersonajesRecycler", "Tamaño de la lista de personajes: " + personajes.size());
            personajesAdapter.establecerLista(personajes);
        });
    }

    /**
     * Observa los datos de los personajes favoritos desde el ViewModel y actualiza el adaptador
     * con los personajes favoritos obtenidos.
     *
     * @param lifecycleOwner El propietario del ciclo de vida que observará los datos.
     */
    public void observarFavoritos(LifecycleOwner lifecycleOwner) {
        personajeViewModel.getFavoritosLiveData().observe(lifecycleOwner, personajesAdapter::establecerLista);
    }

    /**
     * Configura la funcionalidad de búsqueda en la barra de búsqueda del RecyclerView
     * para filtrar los personajes por nombre.
     *
     * @param searchBar La barra de búsqueda {@link SearchView} que se utilizará para filtrar los personajes.
     */
    public void configurarBusqueda(SearchView searchBar) {
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String busqueda) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String busqueda) {
                personajesAdapter.filtradoPorNombre(busqueda);
                return true;
            }
        });
    }

}
