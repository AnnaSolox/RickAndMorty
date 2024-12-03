package com.example.rickandmorty.utils;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.adaptersRecyclerView.LocalizacionesAdapter;
import com.example.rickandmorty.viewmodels.LocalizacionViewModel;

public class RecyclerViewLocalizaciones {
    private RecyclerView recyclerView;
    private LocalizacionesAdapter localizacionesAdapter;
    private LocalizacionViewModel localizacionViewModel;

    public RecyclerViewLocalizaciones(RecyclerView recyclerView, LocalizacionViewModel localizacionViewModel, LocalizacionesAdapter localizacionesAdapter){
        this.recyclerView = recyclerView;
        this.localizacionViewModel = localizacionViewModel;
        this.localizacionesAdapter = localizacionesAdapter;
    }

    public void setupRecyclerView(Context context){
        recyclerView.setAdapter(localizacionesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    public void observarPersonajes(LifecycleOwner lifecycleOwner){
        localizacionViewModel.getLocalizacionLiveData().observe(lifecycleOwner, localizacions -> {
            Log.d("RecyclerLocalizaciones", "Tamaño de la lista de localizaciones: " + localizacions.size());
            localizacionesAdapter.establecerLista(localizacions);
        });
    }
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
