package com.example.rickandmorty.utils;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private Retrofit retrofit;

    public static Retrofit getRetrofitBuilder(){
        Gson gson = GsonConfig.getGson();
        return new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
