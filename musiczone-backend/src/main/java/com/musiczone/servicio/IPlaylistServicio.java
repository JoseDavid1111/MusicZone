package com.musiczone.servicio;

import java.util.List;
import com.musiczone.dto.AgregarCancionPlaylistDto;
import com.musiczone.dto.PlaylistDetalleResponseDto;
import com.musiczone.dto.PlaylistRequestDto;
import com.musiczone.dto.PlaylistResponseDto;

public interface IPlaylistServicio {
    PlaylistResponseDto crearPlaylist(PlaylistRequestDto dto);
    PlaylistResponseDto actualizarPlaylist(Long id, PlaylistRequestDto dto);
    boolean eliminarPlaylist(Long id);
    List<PlaylistResponseDto> listarPorUsuario(Long idUsuario);
    PlaylistDetalleResponseDto verDetalle(Long idPlaylist);
    boolean agregarCancion(Long idPlaylist, AgregarCancionPlaylistDto dto);
    boolean eliminarCancion(Long idPlaylist, Long idCancion);
}
