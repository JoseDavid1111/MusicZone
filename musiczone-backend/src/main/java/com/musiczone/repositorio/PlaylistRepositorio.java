package com.musiczone.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.musiczone.modelo.Playlist;

import java.util.List;

// Se reemplaza JpaRepository por MongoRepository
// El segundo parámetro cambia de Long a String por el ObjectId de MongoDB
public interface PlaylistRepositorio extends MongoRepository<Playlist, String> {

    // En JPA se buscaba por el id del objeto Usuario relacionado
    // En MongoDB el campo usuario es directamente el nombre de usuario (String)
    // por eso el método cambia de findByUsuarioId a findByUsuario
    List<Playlist> findByUsuario(String nombreUsuario);
}
