package com.musiczone.servicio;

import java.util.List;
import com.musiczone.dto.AgregarCancionPlaylistDto;
import com.musiczone.dto.PlaylistDetalleResponseDto;
import com.musiczone.dto.PlaylistRequestDto;
import com.musiczone.dto.PlaylistResponseDto;

// Contrato de operaciones para la gestión de playlists.
public interface IPlaylistServicio {
    PlaylistResponseDto crearPlaylist(PlaylistRequestDto dto);
    PlaylistResponseDto actualizarPlaylist(String id, PlaylistRequestDto dto);
    boolean eliminarPlaylist(String id);

    // Lista las playlists asociadas a un usuario.
    List<PlaylistResponseDto> listarPorUsuario(String nombreUsuario);

    PlaylistDetalleResponseDto verDetalle(String idPlaylist);
    boolean agregarCancion(String idPlaylist, AgregarCancionPlaylistDto dto);

    // Elimina una canción de una playlist.
    boolean eliminarCancion(String idPlaylist, String idCancion);
}
