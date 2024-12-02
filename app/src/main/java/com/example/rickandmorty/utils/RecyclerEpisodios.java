package com.example.rickandmorty.utils;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.adaptersRecyclerView.EpisodiosAdapter;
import com.example.rickandmorty.adaptersRecyclerView.LocalizacionAdapter;
import com.example.rickandmorty.viewmodels.EpisodioViewModel;
import com.example.rickandmorty.viewmodels.PersonajeViewModel;

public class RecyclerEpisodios {
    private RecyclerView recyclerView;
    private EpisodiosAdapter episodiosAdapter;
    private EpisodioViewModel episodioViewModel;

    public RecyclerEpisodios(RecyclerView recyclerView, EpisodioViewModel episodioViewModel, EpisodiosAdapter episodiosAdapter){
        this.recyclerView = recyclerView;
        this.episodioViewModel = episodioViewModel;
        this.episodiosAdapter = episodiosAdapter;
    }

    public void setupRecyclerView(Context context){
        recyclerView.setAdapter(episodiosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public void observarPersonajes(LifecycleOwner lifecycleOwner){
        episodioViewModel.getEpisodiosLiveData().observe(lifecycleOwner, episodios -> {
            Log.d("EpisodiosRecycler", "Tamaño de la lista de episodios: " + episodios.size());
            episodiosAdapter.establecerLista(episodios);
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
                episodiosAdapter.filtradoPorNombre(busqueda);
                return true;
            }
        });
    }
}
