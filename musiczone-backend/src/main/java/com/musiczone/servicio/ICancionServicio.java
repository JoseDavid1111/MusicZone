package com.musiczone.servicio;

import java.util.List;
import com.musiczone.dto.CancionResponseDto;

// Contrato de operaciones de consulta para canciones.
public interface ICancionServicio {

    List<CancionResponseDto> listarTodas();

    // Busca una canción por su identificador.
    CancionResponseDto buscarCancion(String id);

    List<CancionResponseDto> buscarPorArtista(String artista);

    List<CancionResponseDto> buscarPorTitulo(String titulo);
}
