package com.musiczone.servicio;

import java.util.List;
import com.musiczone.dto.CancionResponseDto;

// Se mantiene la misma estructura de interface + implementación
// Solo cambia el tipo del id de Long a String por el ObjectId de MongoDB
public interface ICancionServicio {

    List<CancionResponseDto> listarTodas();

    // El tipo del id cambia de Long a String por el ObjectId de MongoDB
    CancionResponseDto buscarCancion(String id);

    List<CancionResponseDto> buscarPorArtista(String artista);

    List<CancionResponseDto> buscarPorTitulo(String titulo);
}