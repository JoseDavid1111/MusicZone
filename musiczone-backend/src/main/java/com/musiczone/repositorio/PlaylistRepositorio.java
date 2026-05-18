package com.musiczone.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.musiczone.modelo.Playlist;

import java.util.List;

// Repositorio de playlists en MongoDB.
public interface PlaylistRepositorio extends MongoRepository<Playlist, String> {

    // El campo usuario guarda directamente el nombre del usuario asociado.
    List<Playlist> findByUsuario(String nombreUsuario);
}
