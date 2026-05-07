package com.musiczone.servicio;

import java.util.List;
import com.musiczone.dto.CancionResponseDto;

public interface ICancionServicio {

    // Listar todas las canciones
    List<CancionResponseDto> listarTodas();

    // Buscar cancion por id
    CancionResponseDto buscarCancion(Long id);

    // Buscar cancion por artista
    List<CancionResponseDto> buscarPorArtista(String artista);

    // Buscar cancion por titulo
    List<CancionResponseDto> buscarPorTitulo(String titulo);
}
