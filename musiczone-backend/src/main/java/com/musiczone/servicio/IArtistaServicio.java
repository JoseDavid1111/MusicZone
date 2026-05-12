package com.musiczone.servicio;

import java.util.List;
import com.musiczone.dto.ArtistaResponseDto;

// Se mantiene la misma estructura de interface + implementación
// Solo cambia el tipo del id de Long a String por el ObjectId de MongoDB
public interface IArtistaServicio {
    List<ArtistaResponseDto> listarTodos();
    ArtistaResponseDto buscarArtista(String id);
    List<ArtistaResponseDto> buscarPorNombre(String nombre);
}