package com.example.rickandmorty.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;

@Data
public class PersonajeList{
    private Info info;
    @SerializedName("results")
    private ArrayList<Personaje> resultadosPersonaje;
}
