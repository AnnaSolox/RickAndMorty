package com.example.rickandmorty.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;

@Data
public class PersonajeList{
    @SerializedName("results")
    private ArrayList<Personaje> resultadosPersonaje;
}
