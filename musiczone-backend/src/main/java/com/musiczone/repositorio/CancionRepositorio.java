package com.musiczone.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.musiczone.modelo.Cancion;

import java.util.List;

// Repositorio de canciones en MongoDB.
public interface CancionRepositorio extends MongoRepository<Cancion, String> {

    // Busca canciones cuyo título contenga el texto sin importar mayúsculas/minúsculas.
    List<Cancion> findByTituloContainingIgnoreCase(String titulo);

    // Usa una expresión regular para buscar por nombre de artista.
    // artista.nombre accede al campo nombre dentro del objeto artista embebido en el documento cancion
    @Query("{ 'artista.nombre': { $regex: ?0, $options: 'i' } }")
    List<Cancion> buscarPorArtista(String artista);
}
