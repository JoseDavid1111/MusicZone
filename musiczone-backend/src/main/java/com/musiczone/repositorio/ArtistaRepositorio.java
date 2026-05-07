package com.musiczone.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.musiczone.modelo.Artista;

import java.util.List;

public interface ArtistaRepositorio extends JpaRepository<Artista, Long> {
    List<Artista> findByNombreContainingIgnoreCase(String nombre);
}
