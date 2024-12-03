package com.example.rickandmorty.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilitaria para realizar filtrados genéricos en listas de datos.
 * Proporciona un método estático para filtrar elementos en una lista en función de un criterio definido por el usuario.
 */
public class FiltradoUtilidad {

    /**
     * Interfaz funcional que define un criterio de filtrado.
     *
     * @param <T> El tipo de los elementos a filtrar.
     */
    public interface CriterioFiltrado<T> {
        boolean matches(T item, String textoFiltro);
    }

    /**
     * Filtra una lista de elementos en función de un criterio proporcionado por el usuario.
     *
     * @param <T> El tipo de los elementos en la lista.
     * @param listaOriginal La lista original de elementos a filtrar. Si es {@code null}, se trata como una lista vacía.
     * @param textoFiltro El texto utilizado para realizar el filtrado. Si es {@code null} o está vacío, se devuelve una copia de la lista original.
     * @param criterioFiltrado Una implementación de {@link CriterioFiltrado} que define cómo se deben filtrar los elementos.
     * @return Una nueva lista que contiene solo los elementos que cumplen con el criterio de filtrado.
     */
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
