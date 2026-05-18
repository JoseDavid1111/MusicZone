package com.musiczone.servicio;

import java.util.List;
import org.springframework.stereotype.Service;
import com.musiczone.dto.ArtistaResponseDto;
import com.musiczone.modelo.Artista;
import com.musiczone.repositorio.ArtistaRepositorio;

// Servicio de consulta para artistas.
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

    // Busca un artista por su identificador.
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

    // Convierte el documento de artista al DTO usado por la API.
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
