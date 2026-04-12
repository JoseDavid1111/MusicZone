package com.musiczone.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.musiczone.modelo.Playlist;

import java.util.List;

public interface PlaylistRepositorio extends JpaRepository<Playlist, Long> {
    List<Playlist> findByUsuarioId(Long usuarioId);
}
