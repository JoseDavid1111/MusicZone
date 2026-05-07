package com.musiczone.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.musiczone.modelo.Cancion;

import java.util.List;

public interface CancionRepositorio extends JpaRepository<Cancion, Long> {
	
	//Método de busqueda por cancion con coincidencias 
	//O sea, si escribo unas pocas letras ya el buscados da las similitudes
    List<Cancion> findByTituloContainingIgnoreCase(String titulo);
    
    //Metodo de busqueda por artista, devuelve una lista con todas las canciones dl artista
    @Query("""
        SELECT c
        FROM Cancion c
        WHERE LOWER(c.artista.nombre) LIKE LOWER(CONCAT('%', :artista, '%'))
        """)
    List<Cancion> buscarPorArtista(@Param("artista") String artista);
}
