package com.musiczone.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.musiczone.modelo.Cancion;

import java.util.List;

// Se reemplaza JpaRepository por MongoRepository
// El segundo parámetro cambia de Long a String por el ObjectId de MongoDB
public interface CancionRepositorio extends MongoRepository<Cancion, String> {

    // Spring Data MongoDB soporta este método derivado igual que JPA
    // busca canciones cuyo título contenga el texto sin importar mayúsculas/minúsculas
    List<Cancion> findByTituloContainingIgnoreCase(String titulo);

    // En JPA se usaba JPQL con LIKE y LOWER
    // En MongoDB se usa una query con expresión regular para búsqueda parcial sin importar mayúsculas
    // artista.nombre accede al campo nombre dentro del objeto artista embebido en el documento cancion
    @Query("{ 'artista.nombre': { $regex: ?0, $options: 'i' } }")
    List<Cancion> buscarPorArtista(String artista);
}
