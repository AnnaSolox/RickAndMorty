package com.example.rickandmorty.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.rickandmorty.utils.interfacesApi.EpisodiosCallback;
import com.example.rickandmorty.utils.interfacesApi.RMApiService;
import com.example.rickandmorty.models.Episodio;
import com.example.rickandmorty.models.EpisodioList;
import com.example.rickandmorty.utils.GsonConfig;
import com.google.gson.Gson;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EpisodioViewModel extends AndroidViewModel{
    private final RMApiService rmApiService;

    public EpisodioViewModel(@NonNull Application application) {
        super(application);

        Gson gson = GsonConfig.getGson();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        rmApiService = retrofit.create(RMApiService.class);
    }

    /**
     * Realiza una solicitud a la API para obtener la lista de episodios.
     * Cuando recibe la respuesta, procesa los episodios y los agrega a la lista proporcionada.
     * Si no hay más episodios disponibles en páginas posteriores, se cargarán automáticamente.
     *
     * @param callback El callback que maneja la respuesta de la solicitud.
     *
     * @see #procesarRespuesta(Response, List, EpisodiosCallback)
     */
    public void cargarEpisodios(EpisodiosCallback callback) {

        List<Episodio> listadoEpisodios = new ArrayList<>();

        Call<EpisodioList> call = rmApiService.getEpisodioList();
        call.enqueue(new Callback<EpisodioList>() {
            @Override
            public void onResponse(Call<EpisodioList> call, Response<EpisodioList> response) {
                procesarRespuesta(response, listadoEpisodios, callback);
            }

            @Override
            public void onFailure(Call<EpisodioList> call, Throwable throwable) {
                manejarError(throwable);
            }
        });
    }

    /**
     * Comprueba si existe una siguiente página de contenido en la API para seguir cargando Episodios.
     * Si hay más episodios, realiza otra solicitud a la siguiente página.
     *
     * @param siguienteUrl La siguiente página donde se buscarán episodios. Si es nula, no realizará más acciones
     * @param listaEpisodios La lista donde se almacenarán los siguientes resultados
     * @param callback El callback que se utilizará para manejar la respuesta de la solicitud.
     *
     * @see #procesarRespuesta(Response, List, EpisodiosCallback)
     */
    private void cargarSiguientesPaginas(URI siguienteUrl, List<Episodio> listaEpisodios, EpisodiosCallback callback){
        if (siguienteUrl != null) {
            Call<EpisodioList> callSiguiente = rmApiService.getEpisodioListFromUrl(siguienteUrl);
            callSiguiente.enqueue(new Callback<EpisodioList>() {
                @Override
                public void onResponse(Call<EpisodioList> call, Response<EpisodioList> response) {
                    procesarRespuesta(response, listaEpisodios, callback);
                }

                @Override
                public void onFailure(Call<EpisodioList> call, Throwable throwable) {
                    manejarError(throwable);
                }
            });
        }
    }

    /**
     * Procesa la respuesta de la API, agregando los episodios a la lista proporcionada.
     * Si hay más episodios disponibles en páginas siguientes, se cargan automáticamente.
     *
     * @param response La respuesta de la API que contiene la lista de episodios.
     * @param listaEpisodios La lista de episodios donde se almacenarán los episodios obtenidos.
     * @param callback El callback que se llama cuando los episodios se cargan con éxito o si ocurre un error.
     *
     * @see #cargarEpisodios(EpisodiosCallback)
     * @see #cargarSiguientesPaginas(URI, List, EpisodiosCallback)
     */
    private void procesarRespuesta(Response<EpisodioList> response, List<Episodio> listaEpisodios, EpisodiosCallback callback) {
        if (response.body() != null) {
            EpisodioList episodioList = response.body();
            listaEpisodios.addAll(episodioList.getResultadosEpisodio());

            if (episodioList.getInfo().getSiguiente() != null) {
                URI siguienteUrl = episodioList.getInfo().getSiguiente();
                cargarSiguientesPaginas(siguienteUrl, listaEpisodios, callback);
            } else {
                callback.onSuccess(listaEpisodios);
            }
        }
    }

    /**
     * Maneja los errores que ocurren durante la solicitud a la API.
     * Registra un mensaje de error en los logs con el mensaje de excepción proporcionado.
     *
     * @param throwable El objeto que contiene la excepción que ocurrió durante la solicitud a la API.
     *                  El mensaje de esta excepción será registrado en los logs de la aplicación.
     */
    private void manejarError(Throwable throwable){
        Log.e("API ERROR", "EPISODIOS: " + throwable.getMessage());
    }
}
