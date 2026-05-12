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

// Se elimina @Transactional porque MongoDB no usa transacciones relacionales
// Se elimina CancionPlaylistRepositorio porque en MongoDB las canciones
// son un array embebido dentro del documento Playlist, no una colección separada
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
        // En MongoDB se busca el usuario por nombreUsuario en vez de por id
        // porque Playlist guarda el nombre de usuario como referencia ligera
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
        // Se actualiza la fecha de modificación manualmente
        // en JPA esto lo hacía el trigger de PostgreSQL
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
        // En JPA se buscaba por idUsuario (Long)
        // En MongoDB se busca directamente por el nombre de usuario (String)
        return playlistRepositorio.findByUsuario(nombreUsuario)
            .stream()
            .map(this::mapearPlaylist)
            .toList();
    }

    @Override
    public PlaylistDetalleResponseDto verDetalle(String idPlaylist) {
        Playlist playlist = playlistRepositorio.findById(idPlaylist).orElse(null);
        if (playlist == null) return null;

        // En JPA se consultaba CancionPlaylistRepositorio por separado
        // En MongoDB las canciones ya vienen embebidas en el documento playlist
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

        // En JPA se verificaba con CancionPlaylistId si ya existía la relación
        // En MongoDB se verifica si el idCancion ya está en el array de canciones
        boolean yaExiste = playlist.getCanciones().stream()
            .anyMatch(c -> c.getIdCancion().equals(dto.getIdCancion()));
        if (yaExiste) return false;

        CancionPlaylist cp = new CancionPlaylist();
        cp.setIdCancion(cancion.getId());
        cp.setTitulo(cancion.getTitulo());
        cp.setArtista(cancion.getArtista() != null ? cancion.getArtista().getNombre() : null);
        cp.setPosicion(dto.getPosicion());
        cp.setFechaAgregada(LocalDateTime.now());

        playlist.getCanciones().add(cp);
        playlistRepositorio.save(playlist);
        return true;
    }

    @Override
    public boolean eliminarCancion(String idPlaylist, String idCancion) {
        Playlist playlist = playlistRepositorio.findById(idPlaylist).orElse(null);
        if (playlist == null) return false;

        // En JPA se eliminaba el registro de la tabla cancion_playlist por su id compuesto
        // En MongoDB se remueve el elemento del array que tenga el idCancion indicado
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
        return new CancionPlaylistDto(
            cp.getIdCancion(),
            cp.getTitulo(),
            cp.getArtista(),
            cp.getPosicion()
        );
    }
}