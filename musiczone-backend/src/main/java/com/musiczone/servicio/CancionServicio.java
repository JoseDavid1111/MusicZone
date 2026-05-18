package com.musiczone.servicio;

import org.springframework.stereotype.Service;
import com.musiczone.dto.CancionResponseDto;
import com.musiczone.modelo.Cancion;
import com.musiczone.repositorio.CancionRepositorio;

import java.util.List;

// Servicio de consulta para el catálogo de canciones.
@Service
public class CancionServicio implements ICancionServicio {

    private final CancionRepositorio cancionRepositorio;

    public CancionServicio(CancionRepositorio cancionRepositorio) {
        this.cancionRepositorio = cancionRepositorio;
    }

    // Busca una canción por su identificador.
    @Override
    public CancionResponseDto buscarCancion(String id) {
        Cancion cancion = cancionRepositorio.findById(id).orElse(null);
        if (cancion == null) return null;
        return mapearCancion(cancion);
    }

    @Override
    public List<CancionResponseDto> listarTodas() {
        return cancionRepositorio.findAll()
            .stream()
            .map(this::mapearCancion)
            .toList();
    }

    @Override
    public List<CancionResponseDto> buscarPorTitulo(String titulo) {
        return cancionRepositorio.findByTituloContainingIgnoreCase(titulo)
            .stream()
            .map(this::mapearCancion)
            .toList();
    }

    @Override
    public List<CancionResponseDto> buscarPorArtista(String artista) {
        return cancionRepositorio.buscarPorArtista(artista)
            .stream()
            .map(this::mapearCancion)
            .toList();
    }

    // Convierte el documento de canción al DTO usado por la API.
    private CancionResponseDto mapearCancion(Cancion cancion) {
        return new CancionResponseDto(
            cancion.getId(),
            cancion.getTitulo(),
            cancion.getArtista() != null ? cancion.getArtista().getNombre() : null,
            cancion.getAlbum() != null ? cancion.getAlbum().getTitulo() : null,
            cancion.getDuracionSegundos(),
            cancion.getNumeroTrack(),
            cancion.getGenero(),
            cancion.getYearLanzamiento(),
            cancion.getUrlAudio()
        );
    }
}
