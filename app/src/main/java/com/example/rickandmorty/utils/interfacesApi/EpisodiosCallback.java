package com.example.rickandmorty.utils.interfacesApi;

import com.example.rickandmorty.models.Episodio;

import java.util.List;

public interface EpisodiosCallback {
    void onSuccess(List<Episodio> episodios);
}
