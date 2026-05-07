package com.musiczone.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.musiczone.dto.CancionResponseDto;
import com.musiczone.dto.RespuestaApi;
import com.musiczone.servicio.CancionServicio;
import java.util.List;

@RestController
@RequestMapping("/canciones")
public class CancionControlador {

    private final CancionServicio cancionServicio;

    public CancionControlador(CancionServicio cancionServicio) {
        this.cancionServicio = cancionServicio;
    }

    @GetMapping
    public ResponseEntity<RespuestaApi<List<CancionResponseDto>>> listarTodas() {
        List<CancionResponseDto> canciones = cancionServicio.listarTodas();
        return ResponseEntity.ok(new RespuestaApi<>(true, "Canciones obtenidas exitosamente", canciones, HttpStatus.OK.value()));
    }

    @GetMapping("/buscar")
    public ResponseEntity<RespuestaApi<List<CancionResponseDto>>> buscarPorTitulo(
            @RequestParam String titulo) {
        List<CancionResponseDto> canciones = cancionServicio.buscarPorTitulo(titulo);
        return ResponseEntity.ok(new RespuestaApi<>(true, "Busqueda por titulo exitosa", canciones, HttpStatus.OK.value()));
    }

    @GetMapping("/buscar-por-artista")
    public ResponseEntity<RespuestaApi<List<CancionResponseDto>>> buscarPorArtista(
            @RequestParam String artista) {
        List<CancionResponseDto> canciones = cancionServicio.buscarPorArtista(artista);
        return ResponseEntity.ok(new RespuestaApi<>(true, "Busqueda por artista exitosa", canciones, HttpStatus.OK.value()));
    }
}
