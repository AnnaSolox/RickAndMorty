package com.example.rickandmorty.utils;

import java.util.ArrayList;
import java.util.List;

public class FiltradoUtilidad {

    public interface CriterioFiltrado<T> {
        boolean matches(T item, String textoFiltro);
    }

    public static <T> List<T> filtro(List<T> listaOriginal, String textoFiltro, CriterioFiltrado<T> criterioFiltrado){
        if (listaOriginal == null) {
            listaOriginal = new ArrayList<>();
        }

        if (textoFiltro == null || textoFiltro.isEmpty()) {
            return new ArrayList<>(listaOriginal);
        }

        List<T> listaFiltrada = new ArrayList<>();
        for(T item: listaOriginal){
            if(criterioFiltrado.matches(item, textoFiltro)){
                listaFiltrada.add(item);
            }
        }
        return listaFiltrada;
    }
}
