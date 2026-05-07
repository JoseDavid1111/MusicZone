package com.musiczone.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.musiczone.modelo.Album;

import java.util.List;

public interface AlbumRepositorio extends JpaRepository<Album, Long> {
    //Devuelve todos los albumes del artista por su id
	List<Album> findByArtistaId(Long artistaId);
    
	//Busqueda parcial por titulo del album, unas pocas letras buscan la coincidencia
    List<Album> findByTituloContainingIgnoreCase(String titulo);
}
