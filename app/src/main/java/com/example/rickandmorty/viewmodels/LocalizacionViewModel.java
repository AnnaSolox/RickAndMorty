package com.example.rickandmorty.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rickandmorty.models.Localizacion;
import com.example.rickandmorty.models.LocalizacionList;
import com.example.rickandmorty.utils.RMApiService;
import com.example.rickandmorty.utils.RetrofitBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * ViewModel para gestionar la carga de localizaciones desde la API de Rick and Morty.
 * La clase maneja la solicitud de localizaciones, la paginación de la respuesta y la actualización
 * de datos en el UI a través de LiveData.
 */
public class LocalizacionViewModel extends AndroidViewModel {
    private final RMApiService rmApiService;
    @Getter
    private final MutableLiveData<List<Localizacion>> localizacionLiveData = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    /**
     * Constructor del ViewModel. Inicializa el servicio API utilizando Retrofit.
     *
     * @param application La aplicación actual.
     */
    public LocalizacionViewModel(@NonNull Application application) {
        super(application);

        Retrofit retrofit = RetrofitBuilder.getRetrofitBuilder();
        rmApiService = retrofit.create(RMApiService.class);
    }

    /**
     * Obtiene el LiveData que contiene la lista de localizaciones.
     *
     * @return MutableLiveData con la lista de localizaciones.
     */
    public MutableLiveData<List<Localizacion>> obtener() {return localizacionLiveData;}
    MutableLiveData<Localizacion> localizacionSeleccionada = new MutableLiveData<>();

    /**
     * Establece una localización seleccionada.
     *
     * @param localizacion La localización seleccionada.
     */
    public void seleccionar(Localizacion localizacion){
        Log.d("Localizacion_ViewModel", "Localizacion seleccionada " + localizacion.getNombre());
        localizacionSeleccionada.setValue(localizacion);}

    /**
     * Obtiene el LiveData con la localización seleccionada.
     *
     * @return MutableLiveData con la localización seleccionada.
     */
    public MutableLiveData<Localizacion> seleccionada(){return localizacionSeleccionada;}



    /**
     * Inicia la carga de localizaciones desde la API de Rick and Morty.
     * Realiza la solicitud y actualiza el LiveData con la lista de localizaciones.
     * Si existen páginas adicionales, se cargarán automáticamente.
     */
    public void cargarLocalizaciones(){
        isLoading.setValue(true);
        List<Localizacion> listadoLocalizaciones = new ArrayList<>();

        Call<LocalizacionList> call = rmApiService.getLocalizacionList();
        call.enqueue(new Callback<LocalizacionList>() {
            @Override
            public void onResponse(@androidx.annotation.NonNull Call<LocalizacionList> call, @androidx.annotation.NonNull Response<LocalizacionList> response) {
                procesarRespuesta(response, listadoLocalizaciones);
            }

            @Override
            public void onFailure(@androidx.annotation.NonNull Call<LocalizacionList> call, @androidx.annotation.NonNull Throwable throwable) {
                manejarError(throwable);
            }
        });
    }

    /**
     * Carga las siguientes páginas de localizaciones si la respuesta contiene un enlace hacia la siguiente página.
     *
     * @param siguienteUrl La URL de la siguiente página de localizaciones a cargar.
     * @param listaLocalizaciones La lista donde se agregarán las localizaciones obtenidas.
     */
    private void cargarSiguientesPaginas(URI siguienteUrl, List<Localizacion> listaLocalizaciones){
        if(siguienteUrl != null) {
            Call<LocalizacionList> callSiguiente = rmApiService.getLocalizacionFromNextUrl(siguienteUrl);
            callSiguiente.enqueue(new Callback<LocalizacionList>() {
                @Override
                public void onResponse(@androidx.annotation.NonNull Call<LocalizacionList> call, @androidx.annotation.NonNull Response<LocalizacionList> response) {
                    procesarRespuesta(response, listaLocalizaciones);
                }

                @Override
                public void onFailure(@androidx.annotation.NonNull Call<LocalizacionList> call, @androidx.annotation.NonNull Throwable throwable) {
                    manejarError(throwable);
                }
            });
        }
    }

    /**
     * Procesa la respuesta de la API agregando las localizaciones a la lista proporcionada.
     * Si hay más localizaciones disponibles, se realizará una solicitud adicional para obtenerlas.
     *
     * @param response La respuesta de la API que contiene la lista de localizaciones.
     * @param listaLocalizacion La lista de localizaciones donde se agregarán las nuevas localizaciones.
     */
    private void procesarRespuesta(Response<LocalizacionList> response, List<Localizacion> listaLocalizacion){
        if(response.body() != null) {
            LocalizacionList localizacionList = response.body();
            listaLocalizacion.addAll(localizacionList.getResultadosLocalizacion());
            localizacionLiveData.setValue(listaLocalizacion);

            if(localizacionList.getInfo().getSiguiente() != null) {
                URI siguienteUrl = localizacionList.getInfo().getSiguiente();
                cargarSiguientesPaginas(siguienteUrl, listaLocalizacion);
            } else {
                localizacionLiveData.setValue(listaLocalizacion);
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
        Log.e("API ERROR", "LOCALIZACIONES: " + throwable.getMessage());
    }
}
