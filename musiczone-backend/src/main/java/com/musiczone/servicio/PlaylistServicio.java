package com.musiczone.servicio;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.musiczone.dto.AgregarCancionPlaylistDto;
import com.musiczone.dto.CancionPlaylistDto;
import com.musiczone.dto.PlaylistDetalleResponseDto;
import com.musiczone.dto.PlaylistRequestDto;
import com.musiczone.dto.PlaylistResponseDto;
import com.musiczone.modelo.Cancion;
import com.musiczone.modelo.CancionPlaylist;
import com.musiczone.modelo.Playlist;
import com.musiczone.repositorio.CancionRepositorio;
import com.musiczone.repositorio.PlaylistRepositorio;
import com.musiczone.repositorio.UsuarioRepositorio;

// Servicio encargado de crear playlists y administrar sus canciones embebidas.
@Service
public class PlaylistServicio implements IPlaylistServicio {

    private final PlaylistRepositorio playlistRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final CancionRepositorio cancionRepositorio;

    public PlaylistServicio(PlaylistRepositorio playlistRepositorio,
            UsuarioRepositorio usuarioRepositorio,
            CancionRepositorio cancionRepositorio) {
        this.playlistRepositorio = playlistRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.cancionRepositorio = cancionRepositorio;
    }

    @Override
    public PlaylistResponseDto crearPlaylist(PlaylistRequestDto dto) {
        // La playlist guarda el nombre de usuario como referencia ligera.
        var usuario = usuarioRepositorio.findByNombreUsuario(dto.getNombreUsuario()).orElse(null);
        if (usuario == null) return null;

        Playlist playlist = new Playlist();
        playlist.setNombre(dto.getNombre());
        playlist.setDescripcion(dto.getDescripcion());
        playlist.setUsuario(usuario.getNombreUsuario());
        playlist.setFechaCreacion(LocalDateTime.now());
        playlist.setFechaActualizacion(LocalDateTime.now());

        Playlist guardada = playlistRepositorio.save(playlist);
        return mapearPlaylist(guardada);
    }

    @Override
    public PlaylistResponseDto actualizarPlaylist(String id, PlaylistRequestDto dto) {
        Playlist playlist = playlistRepositorio.findById(id).orElse(null);
        if (playlist == null) return null;

        playlist.setNombre(dto.getNombre());
        playlist.setDescripcion(dto.getDescripcion());
        // Se actualiza la fecha de modificación antes de guardar los cambios.
        playlist.setFechaActualizacion(LocalDateTime.now());

        Playlist actualizada = playlistRepositorio.save(playlist);
        return mapearPlaylist(actualizada);
    }

    @Override
    public boolean eliminarPlaylist(String id) {
        if (!playlistRepositorio.existsById(id)) return false;
        playlistRepositorio.deleteById(id);
        return true;
    }

    @Override
    public List<PlaylistResponseDto> listarPorUsuario(String nombreUsuario) {
        // Las playlists se consultan por el nombre de usuario asociado.
        return playlistRepositorio.findByUsuario(nombreUsuario)
            .stream()
            .map(this::mapearPlaylist)
            .toList();
    }

    @Override
    public PlaylistDetalleResponseDto verDetalle(String idPlaylist) {
        Playlist playlist = playlistRepositorio.findById(idPlaylist).orElse(null);
        if (playlist == null) return null;

        // Las canciones ya vienen embebidas en el documento de la playlist.
        List<CancionPlaylistDto> canciones = playlist.getCanciones()
            .stream()
            .sorted((a, b) -> Integer.compare(
                a.getPosicion() != null ? a.getPosicion() : 0,
                b.getPosicion() != null ? b.getPosicion() : 0))
            .map(this::mapearCancionPlaylist)
            .toList();

        return new PlaylistDetalleResponseDto(
            playlist.getId(),
            playlist.getNombre(),
            playlist.getDescripcion(),
            playlist.getUsuario(),
            playlist.getFechaCreacion(),
            playlist.getFechaActualizacion(),
            canciones
        );
    }

    @Override
    public boolean agregarCancion(String idPlaylist, AgregarCancionPlaylistDto dto) {
        Playlist playlist = playlistRepositorio.findById(idPlaylist).orElse(null);
        if (playlist == null) return false;

        Cancion cancion = cancionRepositorio.findById(dto.getIdCancion()).orElse(null);
        if (cancion == null) return false;

        // Evita agregar la misma canción dos veces a la playlist.
        boolean yaExiste = playlist.getCanciones().stream()
            .anyMatch(c -> c.getIdCancion().equals(dto.getIdCancion()));
        if (yaExiste) return false;

        CancionPlaylist cp = new CancionPlaylist();
        cp.setIdCancion(cancion.getId());
        cp.setTitulo(cancion.getTitulo());
        cp.setArtista(cancion.getArtista() != null ? cancion.getArtista().getNombre() : null);
        cp.setPosicion(dto.getPosicion());
        cp.setUrlAudio(cancion.getUrlAudio());
        cp.setFechaAgregada(LocalDateTime.now());

        playlist.getCanciones().add(cp);
        playlistRepositorio.save(playlist);
        return true;
    }

    @Override
    public boolean eliminarCancion(String idPlaylist, String idCancion) {
        Playlist playlist = playlistRepositorio.findById(idPlaylist).orElse(null);
        if (playlist == null) return false;

        // Remueve del arreglo la canción con el id indicado.
        boolean removido = playlist.getCanciones()
            .removeIf(c -> c.getIdCancion().equals(idCancion));
        if (!removido) return false;

        playlistRepositorio.save(playlist);
        return true;
    }

    private PlaylistResponseDto mapearPlaylist(Playlist playlist) {
        return new PlaylistResponseDto(
            playlist.getId(),
            playlist.getNombre(),
            playlist.getDescripcion(),
            playlist.getUsuario(),
            playlist.getFechaCreacion(),
            playlist.getFechaActualizacion()
        );
    }

    private CancionPlaylistDto mapearCancionPlaylist(CancionPlaylist cp) {
        String urlAudio = cp.getUrlAudio();
        if (urlAudio == null || urlAudio.isBlank()) {
            urlAudio = cancionRepositorio.findById(cp.getIdCancion())
                .map(Cancion::getUrlAudio)
                .orElse(null);
        }

        return new CancionPlaylistDto(
            cp.getIdCancion(),
            cp.getTitulo(),
            cp.getArtista(),
            cp.getPosicion(),
            urlAudio
        );
    }
}
