package com.musiczone.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.musiczone.modelo.Artista;

import java.util.List;

// Repositorio de artistas en MongoDB.
public interface ArtistaRepositorio extends MongoRepository<Artista, String> {

    // Busca artistas cuyo nombre contenga el texto sin importar mayúsculas/minúsculas.
    List<Artista> findByNombreContainingIgnoreCase(String nombre);
}
