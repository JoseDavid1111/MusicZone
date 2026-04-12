package com.musiczone.controlador;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.musiczone.dto.RespuestaApi;
import com.musiczone.dto.UsuarioRequestDto;
import com.musiczone.dto.UsuarioResponseDto;
import com.musiczone.servicio.UsuarioServicio;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @PostMapping
    public ResponseEntity<RespuestaApi<UsuarioResponseDto>> registrar(
            @Valid @RequestBody UsuarioRequestDto request) {
        UsuarioResponseDto usuario = usuarioServicio.registrar(request);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new RespuestaApi<>(false, "El usuario o correo ya existe", HttpStatus.CONFLICT.value()));
        }
        return new ResponseEntity<>(new RespuestaApi<>(true, "Usuario registrado exitosamente", usuario, HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }
}
