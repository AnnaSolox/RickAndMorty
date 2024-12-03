package com.example.rickandmorty.utils;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase responsable de la construcción y configuración de una instancia de {@link Retrofit} con
 * la configuración de Gson personalizada para la API de Rick and Morty.
 */
public class RetrofitBuilder {
    /**
     * Crea y devuelve una instancia configurada de {@link Retrofit} con la URL base
     * de la API de Rick and Morty y un convertidor Gson para manejar las respuestas JSON.
     *
     * @return La instancia de {@link Retrofit} configurada.
     */
    public static Retrofit getRetrofitBuilder(){
        Gson gson = GsonConfig.getGson();
        return new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
