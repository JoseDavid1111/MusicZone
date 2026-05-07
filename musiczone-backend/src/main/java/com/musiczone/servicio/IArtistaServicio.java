package com.musiczone.servicio;

import java.util.List;
import com.musiczone.dto.ArtistaResponseDto;

public interface IArtistaServicio {
    List<ArtistaResponseDto> listarTodos();
    ArtistaResponseDto buscarArtista(Long id);
    List<ArtistaResponseDto> buscarPorNombre(String nombre);
}