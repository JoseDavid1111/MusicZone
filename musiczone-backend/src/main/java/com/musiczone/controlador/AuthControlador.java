package com.musiczone.controlador;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.musiczone.dto.LoginRequestDto;
import com.musiczone.dto.LoginResponseDto;
import com.musiczone.dto.RespuestaApi;
import com.musiczone.dto.UsuarioRequestDto;
import com.musiczone.dto.UsuarioResponseDto;
import com.musiczone.servicio.UsuarioServicio;

// Controlador de autenticación — maneja registro y login
// El login ahora devuelve un JWT en el campo token del LoginResponseDto
@RestController
@RequestMapping("/auth")
public class AuthControlador {

    private final UsuarioServicio usuarioServicio;

    public AuthControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    // Registro de nuevo usuario
    // Retorna 400 si el nombre de usuario o correo ya existen
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponseDto> registrar(@RequestBody UsuarioRequestDto dto) {
        UsuarioResponseDto response = usuarioServicio.registrar(dto);
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    // Login — retorna 401 si las credenciales son incorrectas
    // Si es exitoso devuelve los datos del usuario y el JWT en el campo token
    @PostMapping("/login")
    public ResponseEntity<RespuestaApi<LoginResponseDto>> iniciarSesion(
            @Valid @RequestBody LoginRequestDto request) {
        LoginResponseDto datos = usuarioServicio.login(request);
        if (datos == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new RespuestaApi<>(false, "Credenciales invalidas", HttpStatus.UNAUTHORIZED.value()));
        }
        return ResponseEntity.ok(new RespuestaApi<>(true, "Inicio de sesion exitoso", datos, HttpStatus.OK.value()));
    }
}