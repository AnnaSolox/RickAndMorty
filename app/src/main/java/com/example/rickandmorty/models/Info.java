package com.example.rickandmorty.models;

import com.google.gson.annotations.SerializedName;

import java.net.URI;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Info {
    @SerializedName("count")
    private String cantidad;
    @SerializedName("pages")
    private String paginas;
    @SerializedName("next")
    private URI siguiente;
    @SerializedName("prev")
    private URI anterior;
}
