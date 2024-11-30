package com.example.rickandmorty.models;

import com.google.gson.annotations.SerializedName;

import java.net.URI;
import java.util.ArrayList;

import lombok.Data;

@Data
public class LocalizacionList {
    @SerializedName("results")
    private ArrayList<Localizacion> resultadosLocalizacion;

}
