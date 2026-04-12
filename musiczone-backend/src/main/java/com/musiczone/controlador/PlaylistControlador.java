package com.musiczone.controlador;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.musiczone.dto.AgregarCancionPlaylistDto;
import com.musiczone.dto.PlaylistDetalleResponseDto;
import com.musiczone.dto.PlaylistRequestDto;
import com.musiczone.dto.PlaylistResponseDto;
import com.musiczone.dto.RespuestaApi;
import com.musiczone.servicio.PlaylistServicio;
import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistControlador {

    private final PlaylistServicio playlistServicio;

    public PlaylistControlador(PlaylistServicio playlistServicio) {
        this.playlistServicio = playlistServicio;
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<RespuestaApi<List<PlaylistResponseDto>>> listarPorUsuario(
            @PathVariable Long idUsuario) {
        List<PlaylistResponseDto> playlists = playlistServicio.listarPorUsuario(idUsuario);
        return ResponseEntity.ok(new RespuestaApi<>(true, "Playlists obtenidas exitosamente", playlists, HttpStatus.OK.value()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaApi<PlaylistDetalleResponseDto>> verDetalle(
            @PathVariable Long id) {
        PlaylistDetalleResponseDto detalle = playlistServicio.verDetalle(id);
        return ResponseEntity.ok(new RespuestaApi<>(true, "Playlist obtenida exitosamente", detalle, HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<RespuestaApi<PlaylistResponseDto>> crear(
            @Valid @RequestBody PlaylistRequestDto request) {
        PlaylistResponseDto playlist = playlistServicio.crearPlaylist(request);
        return new ResponseEntity<>(new RespuestaApi<>(true, "Playlist creada exitosamente", playlist, HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaApi<PlaylistResponseDto>> actualizar(
            @PathVariable Long id, @Valid @RequestBody PlaylistRequestDto request) {
        PlaylistResponseDto playlist = playlistServicio.actualizarPlaylist(id, request);
        return ResponseEntity.ok(new RespuestaApi<>(true, "Playlist actualizada exitosamente", playlist, HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaApi<Void>> eliminar(@PathVariable Long id) {
        playlistServicio.eliminarPlaylist(id);
        return ResponseEntity.ok(new RespuestaApi<>(true, "Playlist eliminada exitosamente", HttpStatus.OK.value()));
    }

    @PostMapping("/{idPlaylist}/canciones")
    public ResponseEntity<RespuestaApi<Void>> agregarCancion(
            @PathVariable Long idPlaylist,
            @Valid @RequestBody AgregarCancionPlaylistDto request) {
        boolean resultado = playlistServicio.agregarCancion(idPlaylist, request);
        if (!resultado) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RespuestaApi<>(false, "No se pudo agregar la cancion", HttpStatus.BAD_REQUEST.value()));
        }
        return new ResponseEntity<>(new RespuestaApi<>(true, "Cancion agregada exitosamente", HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{idPlaylist}/canciones/{idCancion}")
    public ResponseEntity<RespuestaApi<Void>> eliminarCancion(
            @PathVariable Long idPlaylist,
            @PathVariable Long idCancion) {
        boolean resultado = playlistServicio.eliminarCancion(idPlaylist, idCancion);
        if (!resultado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new RespuestaApi<>(false, "Cancion no encontrada en la playlist", HttpStatus.NOT_FOUND.value()));
        }
        return ResponseEntity.ok(new RespuestaApi<>(true, "Cancion eliminada exitosamente", HttpStatus.OK.value()));
    }
}
