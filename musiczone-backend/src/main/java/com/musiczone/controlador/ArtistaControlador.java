package com.musiczone.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.musiczone.dto.ArtistaResponseDto;
import com.musiczone.dto.RespuestaApi;
import com.musiczone.servicio.ArtistaServicio;
import java.util.List;

@RestController
@RequestMapping("/artistas")
public class ArtistaControlador {

    private final ArtistaServicio artistaServicio;

    public ArtistaControlador(ArtistaServicio artistaServicio) {
        this.artistaServicio = artistaServicio;
    }

    @GetMapping
    public ResponseEntity<RespuestaApi<List<ArtistaResponseDto>>> listarTodos() {
        List<ArtistaResponseDto> artistas = artistaServicio.listarTodos();
        return ResponseEntity.ok(new RespuestaApi<>(true, "Artistas obtenidos exitosamente", artistas, HttpStatus.OK.value()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaApi<ArtistaResponseDto>> buscarArtista(@PathVariable Long id) {
        ArtistaResponseDto artista = artistaServicio.buscarArtista(id);
        if (artista == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new RespuestaApi<>(false, "Artista no encontrado", HttpStatus.NOT_FOUND.value()));
        }
        return ResponseEntity.ok(new RespuestaApi<>(true, "Artista obtenido exitosamente", artista, HttpStatus.OK.value()));
    }

    @GetMapping("/buscar")
    public ResponseEntity<RespuestaApi<List<ArtistaResponseDto>>> buscarPorNombre(@RequestParam String nombre) {
        List<ArtistaResponseDto> artistas = artistaServicio.buscarPorNombre(nombre);
        return ResponseEntity.ok(new RespuestaApi<>(true, "Busqueda por nombre exitosa", artistas, HttpStatus.OK.value()));
    }
}
