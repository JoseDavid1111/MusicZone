package com.musiczone.servicio;

import java.util.List;
import com.musiczone.dto.ArtistaResponseDto;

// Contrato de operaciones de consulta para artistas.
public interface IArtistaServicio {
    List<ArtistaResponseDto> listarTodos();
    ArtistaResponseDto buscarArtista(String id);
    List<ArtistaResponseDto> buscarPorNombre(String nombre);
}
