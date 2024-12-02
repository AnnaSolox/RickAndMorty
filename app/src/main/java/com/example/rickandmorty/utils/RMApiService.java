package com.example.rickandmorty.utils;

import com.example.rickandmorty.models.Episodio;
import com.example.rickandmorty.models.EpisodioList;
import com.example.rickandmorty.models.Localizacion;
import com.example.rickandmorty.models.LocalizacionList;
import com.example.rickandmorty.models.Personaje;
import com.example.rickandmorty.models.PersonajeList;

import java.net.URI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface RMApiService {
    //"https://rickandmortyapi.com/api/";

    //Búsuqeda de lista de elementos de la misma clase
    //endpoint personajes
    @GET("character")
    Call<PersonajeList> getPersonajeList();

    //endpoint localizaciones
    @GET("location")
    Call<LocalizacionList> getLocalizacionList();

    //endpoint episodios
    @GET("episode")
    Call<EpisodioList> getEpisodioList();

    //Cargar siguientes paginas de los modelos
    @GET
    Call<EpisodioList> getEpisodioListFromUrl(@Url URI url);

    @GET
    Call<LocalizacionList> getLocalizacionFromNextUrl(@Url URI url);

    @GET
    Call<PersonajeList> getPersonajesFromNextUrl(@Url URI url);


    //Busqueda de elementos por ID
    //endpoint personaje por id
    @GET("character/{id}")
    Call<Personaje> getPersonajeById(@Path("id") String id);

    //endpoint localizacion por id
    @GET("location/{id}")
    Call<Localizacion> getLocalizacionById(@Path("id") String id);

    //endpoint episodio por id
    @GET("episode/{id}")
    Call<Episodio> getEpisodioById(@Path("id") String id);

}
