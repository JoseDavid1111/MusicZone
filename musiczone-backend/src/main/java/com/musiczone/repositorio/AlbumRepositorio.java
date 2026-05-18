package com.musiczone.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.musiczone.modelo.Album;

import java.util.List;

// Repositorio de álbumes en MongoDB.
public interface AlbumRepositorio extends MongoRepository<Album, String> {

    // En MongoDB los campos embebidos se consultan con notación de punto
    // artista.id busca dentro del objeto artista embebido en el documento album
    List<Album> findByArtistaId(String artistaId);

    // Método derivado para buscar álbumes por título.
    List<Album> findByTituloContainingIgnoreCase(String titulo);
}
