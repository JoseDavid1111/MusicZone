package com.musiczone.servicio;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.musiczone.dto.AgregarCancionPlaylistDto;
import com.musiczone.dto.CancionPlaylistDto;
import com.musiczone.dto.PlaylistDetalleResponseDto;
import com.musiczone.dto.PlaylistRequestDto;
import com.musiczone.dto.PlaylistResponseDto;
import com.musiczone.modelo.Cancion;
import com.musiczone.modelo.CancionPlaylist;
import com.musiczone.modelo.CancionPlaylistId;
import com.musiczone.modelo.Playlist;
import com.musiczone.modelo.Usuario;
import com.musiczone.repositorio.CancionPlaylistRepositorio;
import com.musiczone.repositorio.CancionRepositorio;
import com.musiczone.repositorio.PlaylistRepositorio;
import com.musiczone.repositorio.UsuarioRepositorio;

@Service
@Transactional
public class PlaylistServicio implements IPlaylistServicio {

    private final PlaylistRepositorio playlistRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final CancionRepositorio cancionRepositorio;
    private final CancionPlaylistRepositorio cancionPlaylistRepositorio;

    public PlaylistServicio(PlaylistRepositorio playlistRepositorio,
            UsuarioRepositorio usuarioRepositorio,
            CancionRepositorio cancionRepositorio,
            CancionPlaylistRepositorio cancionPlaylistRepositorio) {
        this.playlistRepositorio = playlistRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.cancionRepositorio = cancionRepositorio;
        this.cancionPlaylistRepositorio = cancionPlaylistRepositorio;
    }

    @Override
    public PlaylistResponseDto crearPlaylist(PlaylistRequestDto dto) {
        Usuario usuario = usuarioRepositorio.findById(dto.getIdUsuario()).orElse(null);
        if (usuario == null) return null;

        Playlist playlist = new Playlist();
        playlist.setNombre(dto.getNombre());
        playlist.setDescripcion(dto.getDescripcion());
        playlist.setUsuario(usuario);
        playlist.setFechaCreacion(LocalDateTime.now());
        playlist.setFechaActualizacion(LocalDateTime.now());

        Playlist guardada = playlistRepositorio.save(playlist);
        return mapearPlaylist(guardada);
    }

    @Override
    public PlaylistResponseDto actualizarPlaylist(Long id, PlaylistRequestDto dto) {
        Playlist playlist = playlistRepositorio.findById(id).orElse(null);
        if (playlist == null) return null;

        playlist.setNombre(dto.getNombre());
        playlist.setDescripcion(dto.getDescripcion());

        Playlist actualizada = playlistRepositorio.save(playlist);
        return mapearPlaylist(actualizada);
    }

    @Override
    public boolean eliminarPlaylist(Long id) {
        if (!playlistRepositorio.existsById(id)) return false;
        playlistRepositorio.deleteById(id);
        return true;
    }

    @Override
    public List<PlaylistResponseDto> listarPorUsuario(Long idUsuario) {
        return playlistRepositorio.findByUsuarioId(idUsuario)
            .stream()
            .map(this::mapearPlaylist)
            .toList();
    }

    @Override
    public PlaylistDetalleResponseDto verDetalle(Long idPlaylist) {
        Playlist playlist = playlistRepositorio.findById(idPlaylist).orElse(null);
        if (playlist == null) return null;

        List<CancionPlaylistDto> canciones = cancionPlaylistRepositorio
            .findByPlaylistIdOrderByPosicionAsc(idPlaylist)
            .stream()
            .map(this::mapearCancionPlaylist)
            .toList();

        return new PlaylistDetalleResponseDto(
            playlist.getId(),
            playlist.getNombre(),
            playlist.getDescripcion(),
            playlist.getUsuario().getId(),
            playlist.getUsuario().getNombreUsuario(),
            playlist.getFechaCreacion(),
            playlist.getFechaActualizacion(),
            canciones
        );
    }

    @Override
    public boolean agregarCancion(Long idPlaylist, AgregarCancionPlaylistDto dto) {
        Playlist playlist = playlistRepositorio.findById(idPlaylist).orElse(null);
        if (playlist == null) return false;

        Cancion cancion = cancionRepositorio.findById(dto.getIdCancion()).orElse(null);
        if (cancion == null) return false;

        CancionPlaylistId cpId = new CancionPlaylistId(idPlaylist, dto.getIdCancion());
        if (cancionPlaylistRepositorio.existsById(cpId)) return false;

        CancionPlaylist cp = new CancionPlaylist();
        cp.setId(cpId);
        cp.setPlaylist(playlist);
        cp.setCancion(cancion);
        cp.setPosicion(dto.getPosicion());
        cp.setFechaAgregada(LocalDateTime.now());

        cancionPlaylistRepositorio.save(cp);
        return true;
    }

    @Override
    public boolean eliminarCancion(Long idPlaylist, Long idCancion) {
        CancionPlaylistId cpId = new CancionPlaylistId(idPlaylist, idCancion);
        if (!cancionPlaylistRepositorio.existsById(cpId)) return false;
        cancionPlaylistRepositorio.deleteById(cpId);
        return true;
    }

    private PlaylistResponseDto mapearPlaylist(Playlist playlist) {
        return new PlaylistResponseDto(
            playlist.getId(),
            playlist.getNombre(),
            playlist.getDescripcion(),
            playlist.getUsuario().getId(),
            playlist.getUsuario().getNombreUsuario(),
            playlist.getFechaCreacion(),
            playlist.getFechaActualizacion()
        );
    }

    private CancionPlaylistDto mapearCancionPlaylist(CancionPlaylist cp) {
        return new CancionPlaylistDto(
            cp.getCancion().getId(),
            cp.getCancion().getTitulo(),
            cp.getCancion().getArtista().getNombre(),
            cp.getCancion().getAlbum() != null ? cp.getCancion().getAlbum().getTitulo() : null,
            cp.getPosicion()
        );
    }
}
