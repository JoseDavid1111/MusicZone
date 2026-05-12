package com.musiczone.servicio;

import java.util.List;
import com.musiczone.dto.AgregarCancionPlaylistDto;
import com.musiczone.dto.PlaylistDetalleResponseDto;
import com.musiczone.dto.PlaylistRequestDto;
import com.musiczone.dto.PlaylistResponseDto;

// Se mantiene la misma estructura de interface + implementación
// Los tipos de id cambian de Long a String por el ObjectId de MongoDB
public interface IPlaylistServicio {
    PlaylistResponseDto crearPlaylist(PlaylistRequestDto dto);
    PlaylistResponseDto actualizarPlaylist(String id, PlaylistRequestDto dto);
    boolean eliminarPlaylist(String id);

    // Cambia de idUsuario (Long) a nombreUsuario (String)
    // En MongoDB las playlists se buscan por nombre de usuario, no por id
    List<PlaylistResponseDto> listarPorUsuario(String nombreUsuario);

    PlaylistDetalleResponseDto verDetalle(String idPlaylist);
    boolean agregarCancion(String idPlaylist, AgregarCancionPlaylistDto dto);

    // idCancion cambia de Long a String por el ObjectId de MongoDB
    boolean eliminarCancion(String idPlaylist, String idCancion);
}