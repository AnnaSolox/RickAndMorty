package com.example.rickandmorty.viewmodels;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rickandmorty.models.Episodio;
import com.example.rickandmorty.models.Personaje;
import com.example.rickandmorty.models.PersonajeList;
import com.example.rickandmorty.utils.FavoritosJsonUtilidad;
import com.example.rickandmorty.utils.RMApiService;
import com.example.rickandmorty.utils.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PersonajeViewModel extends AndroidViewModel {
    private final RMApiService rmApiService;
    @Getter
    private final MutableLiveData<List<Personaje>> personajeLiveData = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
     @Getter
    private final Set<Personaje> favoritos = new HashSet<>();
     @Getter
    MutableLiveData<List<Personaje>> favoritosLiveData = new MutableLiveData<>();

    public PersonajeViewModel(@NonNull Application application) {
        super(application);
        Retrofit retrofit = RetrofitBuilder.getRetrofitBuilder();
        rmApiService = retrofit.create(RMApiService.class);
        favoritos.addAll(FavoritosJsonUtilidad.cargarFavoritos(application.getApplicationContext()));
        favoritosLiveData.setValue(new ArrayList<>(favoritos));
    }

    //Cargar Personaje individual:

    public MutableLiveData<List<Personaje>> obtener() {return personajeLiveData;}
    MutableLiveData<Personaje> personajeSeleccionado = new MutableLiveData<>();

    public void seleccionar(Personaje personaje){
        Log.d("Personaje_ViewModel", "Personaje seleccionado " + personaje.getNombre());
        personajeSeleccionado.setValue(personaje);}
    public MutableLiveData<Personaje> seleccionado(){return personajeSeleccionado;}

    public void cargarPerrsonajes(){
        isLoading.setValue(true);
        List<Personaje> listaPersonajes = new ArrayList<>();

        Call<PersonajeList> call = rmApiService.getPersonajeList();
        call.enqueue(new Callback<PersonajeList>() {
            @Override
            public void onResponse(@NonNull Call<PersonajeList> call, @NonNull Response<PersonajeList> response) {
                procesarRespuesta(response, listaPersonajes);
            }

            @Override
            public void onFailure(@NonNull Call<PersonajeList> call, @NonNull Throwable throwable) {
                manejarError(throwable);
            }
        });
    }

    private void cargarSiguientesPaginas(URI siguienteUrl, List<Personaje> listaPersonajes){
        if(siguienteUrl != null) {
            Call<PersonajeList> callSiguiente = rmApiService.getPersonajesFromNextUrl(siguienteUrl);
            callSiguiente.enqueue(new Callback<PersonajeList>() {
                @Override
                public void onResponse(@NonNull Call<PersonajeList> call, @NonNull Response<PersonajeList> response) {
                    procesarRespuesta(response, listaPersonajes);
                }

                @Override
                public void onFailure(@NonNull Call<PersonajeList> call, @NonNull Throwable throwable) {
                    manejarError(throwable);
                }
            });
        }
    }

    private void procesarRespuesta(Response<PersonajeList> response, List<Personaje> listaPersonaje){
        if (response.body() != null) {
            PersonajeList personajeList = response.body();
            listaPersonaje.addAll(personajeList.getResultadosPersonaje());
            personajeLiveData.setValue(listaPersonaje);

            if (personajeList.getInfo().getSiguiente() != null) {
                URI siguienteUrl = personajeList.getInfo().getSiguiente();
                cargarSiguientesPaginas(siguienteUrl, listaPersonaje);
            } else {
                personajeLiveData.setValue(listaPersonaje);
                isLoading.setValue(false);
            }
        }
    }

    private void manejarError(Throwable throwable){
        Log.e("API ERROR", "PERSONAJES: " + throwable.getMessage());
    }

    public void cargarPersonajesPorIds(List<String> urls) {
        isLoading.setValue(true);
        List<Personaje> listaPersonajes = new ArrayList<>();

        Log.d("PersonajesViewModel", "Cargando personajes por ID: " + urls.toString());

        if (urls == null || urls.isEmpty()) {
            Log.e("PersonajeViewModel", "La lista de residentes está vacía o nula.");
            isLoading.setValue(false);
            return;
        }

        for(String url: urls) {
            String id = url.substring(url.lastIndexOf("/")+1);
            Log.d("PersonajeViewModel", "Cargando personaje con ID: " + id);
            Call<Personaje> call = rmApiService.getPersonajeById(id);
            call.enqueue(new Callback<Personaje>() {
                @Override
                public void onResponse(Call<Personaje> call, Response<Personaje> response) {
                    if (response.body() != null) {
                        listaPersonajes.add(response.body());
                        Log.d("PersonajeViewModel", "Personaje cargado: " + response.body().getNombre());
                        personajeLiveData.setValue(listaPersonajes);
                    } else {
                        Log.e("PersonajeViewModel", "Respuesta vacía para el personaje ID: " + id);
                    }

                    if (listaPersonajes.size() == urls.size()){
                        Log.d("PersonajeViewModel", "Todos los personajes han sido cargados.");
                        isLoading.setValue(false);
                    }
                }

                @Override
                public void onFailure(Call<Personaje> call, Throwable throwable) {
                    Log.e("PersonajeViewModel", "Error al cargar personaje ID: " + id + " Error: " + throwable.getMessage());
                    manejarError(throwable);
                }
            });
        }
    }

    //Añadir personajes a favoritos
    public void toggleFavorito(Personaje personaje) {
        if (favoritos.contains(personaje)) {
            favoritos.remove(personaje);
        } else {
            favoritos.add(personaje);
        }

        favoritosLiveData.setValue(new ArrayList<>(favoritos));

        FavoritosJsonUtilidad.guardarFavoritos(getApplication().getApplicationContext(), favoritos);
    }

    public boolean esFavorito(Personaje personaje) {
        return favoritos.contains(personaje);
    }


}
