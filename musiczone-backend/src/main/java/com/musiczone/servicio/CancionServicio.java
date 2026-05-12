package com.musiczone.servicio;

import org.springframework.stereotype.Service;
import com.musiczone.dto.CancionResponseDto;
import com.musiczone.modelo.Cancion;
import com.musiczone.repositorio.CancionRepositorio;

import java.util.List;

// Se elimina @Transactional porque MongoDB no usa transacciones relacionales
@Service
public class CancionServicio implements ICancionServicio {

    private final CancionRepositorio cancionRepositorio;

    public CancionServicio(CancionRepositorio cancionRepositorio) {
        this.cancionRepositorio = cancionRepositorio;
    }

    // El tipo del id cambia de Long a String por el ObjectId de MongoDB
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

    // Método privado que convierte Cancion a CancionResponseDto
    // Se accede con ?. a artista y album porque pueden ser null (canciones sin album o sin artista)
    // Se agrega urlAudio para que el frontend pueda reproducir la cancion directamente
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
