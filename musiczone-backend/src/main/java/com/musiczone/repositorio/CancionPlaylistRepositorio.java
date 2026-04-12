package com.musiczone.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.musiczone.modelo.CancionPlaylist;
import com.musiczone.modelo.CancionPlaylistId;

import java.util.List;

public interface CancionPlaylistRepositorio extends JpaRepository<CancionPlaylist, CancionPlaylistId> {
    //Todas las canciones de una playlist
	List<CancionPlaylist> findByPlaylistId(Long playlistId);
    
	//Enn que playlist esta una cancion especifica
	List<CancionPlaylist> findByCancionId(Long cancionId);
    
	//Las canciones de una playlist ordenadas por posicion
	List<CancionPlaylist> findByPlaylistIdOrderByPosicionAsc(Long playlistId);
}
