package com.example.rickandmorty.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rickandmorty.models.Personaje;
import com.example.rickandmorty.models.PersonajeList;
import com.example.rickandmorty.utils.FavoritosJsonUtilidad;
import com.example.rickandmorty.utils.RMApiService;
import com.example.rickandmorty.utils.RetrofitBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * ViewModel para gestionar la carga y la lógica de los personajes de la API de Rick and Morty.
 * Esta clase se encarga de cargar los personajes desde la API, gestionar los favoritos
 * y exponer los datos a la vista mediante LiveData.
 */
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

    /**
     * Constructor del ViewModel.
     * Inicializa el servicio de API y carga los favoritos almacenados.
     *
     * @param application Contexto de la aplicación.
     */
    public PersonajeViewModel(@NonNull Application application) {
        super(application);
        Retrofit retrofit = RetrofitBuilder.getRetrofitBuilder();
        rmApiService = retrofit.create(RMApiService.class);
        favoritos.addAll(FavoritosJsonUtilidad.cargarFavoritos(application.getApplicationContext()));
        favoritosLiveData.setValue(new ArrayList<>(favoritos));
    }

    //Cargar Personaje individual:

    MutableLiveData<Personaje> personajeSeleccionado = new MutableLiveData<>();

    /**
     * Establece el personaje seleccionado.
     *
     * @param personaje El personaje seleccionado.
     */
    public void seleccionar(Personaje personaje){
        Log.d("Personaje_ViewModel", "Personaje seleccionado " + personaje.getNombre());
        personajeSeleccionado.setValue(personaje);}

    /**
     * Obtiene el LiveData del personaje seleccionado.
     *
     * @return LiveData del personaje seleccionado.
     */
    public MutableLiveData<Personaje> seleccionado(){return personajeSeleccionado;}

    /**
     * Inicia la carga de personajes desde la API de Rick and Morty.
     * Realiza la solicitud y actualiza el LiveData con la lista de personajes.
     * Si existen páginas adicionales, se cargarán automáticamente.
     */
    public void cargarPersonajes(){
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

    /**
     * Carga las siguientes páginas de personajes si la API devuelve más resultados.
     *
     * @param siguienteUrl La URL de la siguiente página.
     * @param listaPersonajes La lista de personajes cargados hasta ahora.
     */
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

    /**
     * Procesa la respuesta de la API y actualiza el LiveData con los resultados.
     *
     * @param response La respuesta de la API.
     * @param listaPersonaje La lista de personajes a actualizar.
     */
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

    /**
     * Maneja los errores de la carga de datos.
     *
     * @param throwable El error que ocurrió.
     */
    private void manejarError(Throwable throwable){
        Log.e("API ERROR", "PERSONAJES: " + throwable.getMessage());
    }

    /**
     * Carga personajes basados en una lista de IDs.
     *
     * @param urls Lista de URLs de los personajes a cargar.
     */
    public void cargarPersonajesPorIds(List<String> urls) {
        isLoading.setValue(true);
        List<Personaje> listaPersonajes = new ArrayList<>();

        Log.d("PersonajesViewModel", "Cargando personajes por ID: " + urls.toString());

        if (urls.isEmpty()) {
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
                public void onResponse(@NonNull Call<Personaje> call, @NonNull Response<Personaje> response) {
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
                public void onFailure(@NonNull Call<Personaje> call, @NonNull Throwable throwable) {
                    Log.e("PersonajeViewModel", "Error al cargar personaje ID: " + id + " Error: " + throwable.getMessage());
                    manejarError(throwable);
                }
            });
        }
    }

    /**
     * Añade o elimina un personaje de la lista de favoritos.
     *
     * @param personaje El personaje a añadir o eliminar de favoritos.
     */
    public void toggleFavorito(Personaje personaje) {
        if (favoritos.contains(personaje)) {
            favoritos.remove(personaje);
        } else {
            favoritos.add(personaje);
        }

        favoritosLiveData.setValue(new ArrayList<>(favoritos));

        FavoritosJsonUtilidad.guardarFavoritos(getApplication().getApplicationContext(), favoritos);
    }

    /**
     * Verifica si un personaje está en la lista de favoritos.
     *
     * @param personaje El personaje a verificar.
     * @return Verdadero si el personaje es favorito, falso en caso contrario.
     */
    public boolean esFavorito(Personaje personaje) {
        return favoritos.contains(personaje);
    }


}
