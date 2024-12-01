package com.example.rickandmorty.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rickandmorty.models.Episodio;
import com.example.rickandmorty.models.EpisodioList;
import com.example.rickandmorty.utils.RMApiService;
import com.example.rickandmorty.utils.RetrofitBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * ViewModel para gestionar la carga de episodios desde la API de Rick and Morty.
 * La clase maneja la solicitud de episodios, la paginación de la respuesta y la actualización
 * de datos en el UI a través de LiveData.
 */
public class EpisodioViewModel extends AndroidViewModel{
    private final RMApiService rmApiService;
    @Getter
    private final MutableLiveData<List<Episodio>> episodiosLiveData = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    /**
     * Constructor del ViewModel. Inicializa el servicio API utilizando Retrofit.
     *
     * @param application La aplicación actual.
     */
    public EpisodioViewModel(@NonNull Application application) {
        super(application);

        Retrofit retrofit = RetrofitBuilder.getRetrofitBuilder();
        rmApiService = retrofit.create(RMApiService.class);
    }

    /**
     * Inicia la carga de episodios desde la API de Rick and Morty.
     * Realiza la solicitud y actualiza el LiveData con la lista de episodios.
     * Si existen páginas adicionales, se cargarán automáticamente.
     */
    public void cargarEpisodios() {
        isLoading.setValue(true);
        List<Episodio> listadoEpisodios = new ArrayList<>();

        Call<EpisodioList> call = rmApiService.getEpisodioList();
        call.enqueue(new Callback<EpisodioList>() {
            @Override
            public void onResponse(@NonNull Call<EpisodioList> call, @NonNull Response<EpisodioList> response) {
                procesarRespuesta(response, listadoEpisodios);
            }

            @Override
            public void onFailure(@NonNull Call<EpisodioList> call, @NonNull Throwable throwable) {
                manejarError(throwable);
            }
        });
    }

    /**
     * Carga las siguientes páginas de episodios si la respuesta contiene un enlace hacia la siguiente página.
     *
     * @param siguienteUrl La URL de la siguiente página de episodios a cargar.
     * @param listaEpisodios La lista donde se agregarán los episodios obtenidos.
     */
    private void cargarSiguientesPaginas(URI siguienteUrl, List<Episodio> listaEpisodios){
        if (siguienteUrl != null) {
            Call<EpisodioList> callSiguiente = rmApiService.getEpisodioListFromUrl(siguienteUrl);
            callSiguiente.enqueue(new Callback<EpisodioList>() {
                @Override
                public void onResponse(@NonNull Call<EpisodioList> call, @NonNull Response<EpisodioList> response) {
                    procesarRespuesta(response, listaEpisodios);
                }

                @Override
                public void onFailure(@NonNull Call<EpisodioList> call, @NonNull Throwable throwable) {
                    manejarError(throwable);
                }
            });
        }
    }

    /**
     * Procesa la respuesta de la API agregando los episodios a la lista proporcionada.
     * Si hay más episodios disponibles, se realizará una solicitud adicional para obtenerlos.
     *
     * @param response La respuesta de la API que contiene la lista de episodios.
     * @param listaEpisodios La lista de episodios donde se agregarán los nuevos episodios.
     */
    private void procesarRespuesta(Response<EpisodioList> response, List<Episodio> listaEpisodios) {
        if (response.body() != null) {
            EpisodioList episodioList = response.body();
            listaEpisodios.addAll(episodioList.getResultadosEpisodio());
            episodiosLiveData.setValue(listaEpisodios);

            if (episodioList.getInfo().getSiguiente() != null) {
                URI siguienteUrl = episodioList.getInfo().getSiguiente();
                cargarSiguientesPaginas(siguienteUrl, listaEpisodios);
            } else {
                episodiosLiveData.setValue(listaEpisodios);
                isLoading.setValue(false);
            }
        }
    }

    /**
     * Maneja los errores que ocurren durante la solicitud a la API.
     * Registra un mensaje de error en los logs con el mensaje de excepción proporcionado.
     *
     * @param throwable El objeto que contiene la excepción que ocurrió durante la solicitud a la API.
     */
    private void manejarError(Throwable throwable){
        Log.e("API ERROR", "EPISODIOS: " + throwable.getMessage());
    }
}
