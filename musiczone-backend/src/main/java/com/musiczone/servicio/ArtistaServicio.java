package com.musiczone.servicio;

import java.util.List;
import org.springframework.stereotype.Service;
import com.musiczone.dto.ArtistaResponseDto;
import com.musiczone.modelo.Artista;
import com.musiczone.repositorio.ArtistaRepositorio;

// Se elimina @Transactional porque MongoDB no usa transacciones relacionales
// Spring Data MongoDB maneja las operaciones directamente sin necesidad de esta anotación
@Service
public class ArtistaServicio implements IArtistaServicio {

    private final ArtistaRepositorio artistaRepositorio;

    public ArtistaServicio(ArtistaRepositorio artistaRepositorio) {
        this.artistaRepositorio = artistaRepositorio;
    }

    @Override
    public List<ArtistaResponseDto> listarTodos() {
        return artistaRepositorio.findAll()
            .stream()
            .map(this::mapearArtista)
            .toList();
    }

    // El tipo del id cambia de Long a String por el ObjectId de MongoDB
    @Override
    public ArtistaResponseDto buscarArtista(String id) {
        Artista artista = artistaRepositorio.findById(id).orElse(null);
        if (artista == null) return null;
        return mapearArtista(artista);
    }

    @Override
    public List<ArtistaResponseDto> buscarPorNombre(String nombre) {
        return artistaRepositorio.findByNombreContainingIgnoreCase(nombre)
            .stream()
            .map(this::mapearArtista)
            .toList();
    }

    // Método privado que convierte un objeto Artista a ArtistaResponseDto
    // evita exponer directamente el modelo en las respuestas de la API
    private ArtistaResponseDto mapearArtista(Artista artista) {
        return new ArtistaResponseDto(
            artista.getId(),
            artista.getNombre(),
            artista.getGenero(),
            artista.getPais(),
            artista.getBio(),
            artista.getPerfilUrl()
        );
    }
}