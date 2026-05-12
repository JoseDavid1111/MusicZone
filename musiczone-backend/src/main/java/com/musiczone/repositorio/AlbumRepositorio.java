package com.musiczone.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.musiczone.modelo.Album;

import java.util.List;

// Se reemplaza JpaRepository por MongoRepository
// El segundo parámetro cambia de Long a String porque MongoDB usa ObjectId como identificador
public interface AlbumRepositorio extends MongoRepository<Album, String> {

    // En MongoDB los campos embebidos se consultan con notación de punto
    // artista.id busca dentro del objeto artista embebido en el documento album
    List<Album> findByArtistaId(String artistaId);

    // Este método funciona igual que en JPA
    // Spring Data MongoDB soporta los mismos métodos derivados por nombre
    List<Album> findByTituloContainingIgnoreCase(String titulo);
}
