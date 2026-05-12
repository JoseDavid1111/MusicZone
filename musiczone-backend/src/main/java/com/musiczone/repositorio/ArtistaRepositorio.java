package com.musiczone.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.musiczone.modelo.Artista;

import java.util.List;

// Se reemplaza JpaRepository por MongoRepository
// El segundo parámetro cambia de Long a String por el ObjectId de MongoDB
public interface ArtistaRepositorio extends MongoRepository<Artista, String> {

    // Spring Data MongoDB soporta este método derivado igual que JPA
    // busca artistas cuyo nombre contenga el texto sin importar mayúsculas/minúsculas
    List<Artista> findByNombreContainingIgnoreCase(String nombre);
}
