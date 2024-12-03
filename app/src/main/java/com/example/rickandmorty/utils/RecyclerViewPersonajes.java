package com.example.rickandmorty.utils;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.adaptersRecyclerView.PersonajesAdapter;
import com.example.rickandmorty.viewmodels.PersonajeViewModel;

public class RecyclerViewPersonajes {
    private final RecyclerView recyclerView;
    private final PersonajesAdapter personajesAdapter;
    private final PersonajeViewModel personajeViewModel;

    public RecyclerViewPersonajes(RecyclerView recyclerView, PersonajeViewModel personajeViewModel, PersonajesAdapter personajesAdapter){
        this.recyclerView = recyclerView;
        this.personajeViewModel = personajeViewModel;
        this.personajesAdapter = personajesAdapter;
    }

    public void setupRecyclerView(Context context){
        recyclerView.setAdapter(personajesAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
    }

    public void observarPersonajes(LifecycleOwner lifecycleOwner){
        personajeViewModel.getPersonajeLiveData().observe(lifecycleOwner, personajes -> {
            Log.d("PersonajesRecycler", "Tamaño de la lista de personajes: " + personajes.size());
            personajesAdapter.establecerLista(personajes);
        });
    }

    public void observarFavoritos(LifecycleOwner lifecycleOwner) {
        personajeViewModel.getFavoritosLiveData().observe(lifecycleOwner, personajesAdapter::establecerLista);
    }

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
