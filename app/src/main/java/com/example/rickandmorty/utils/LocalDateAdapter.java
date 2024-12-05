package com.example.rickandmorty.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Adaptador personalizado de Gson para serializar y deserializar objetos {@link LocalDate}.
 * Este adaptador convierte un objeto {@link LocalDate} a su representación en formato
 * JSON y viceversa, utilizando un formato de fecha específico.
 */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);

    /**
     * Serializa un objeto {@link LocalDate} en formato JSON.
     *
     * @param out El escritor de JSON al que se enviará la salida.
     * @param value El objeto {@link LocalDate} a serializar.
     * @throws IOException Si ocurre un error de entrada/salida durante la escritura.
     */
    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        out.value(value.format(FORMATTER));
    }

    /**
     * Deserializa un objeto {@link LocalDate} desde un formato JSON.
     *
     * @param in El lector de JSON desde el que se leerá la entrada.
     * @return Un objeto {@link LocalDate} deserializado.
     * @throws IOException Si ocurre un error de entrada/salida durante la lectura.
     */
    @Override
    public LocalDate read(JsonReader in) throws IOException {
        String dateStr = in.nextString();
        return LocalDate.parse(dateStr, FORMATTER);
    }
}