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

public class LocalizacionViewModel extends AndroidViewModel {
    private final RMApiService rmApiService;
    @Getter
    private final MutableLiveData<List<Localizacion>> localizacionLiveData = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LocalizacionViewModel(@NonNull Application application) {
        super(application);

        Retrofit retrofit = RetrofitBuilder.getRetrofitBuilder();
        rmApiService = retrofit.create(RMApiService.class);
    }

    //Cargar Localización individual:

    public MutableLiveData<List<Localizacion>> obtener() {return localizacionLiveData;}
    MutableLiveData<Localizacion> localizacionSeleccionada = new MutableLiveData<>();

    public void seleccionar(Localizacion localizacion){
        Log.d("Localizacion_ViewModel", "Localizacion seleccionada " + localizacion.getNombre());
        localizacionSeleccionada.setValue(localizacion);}
    public MutableLiveData<Localizacion> seleccionada(){return localizacionSeleccionada;}



    //Cargar datos de la API:
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

    private void manejarError(Throwable throwable){
        Log.e("API ERROR", "LOCALIZACIONES: " + throwable.getMessage());
    }
}
