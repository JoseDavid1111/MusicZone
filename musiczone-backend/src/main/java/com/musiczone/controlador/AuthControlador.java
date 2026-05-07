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

@RestController
@RequestMapping("/auth")
public class AuthControlador {

    private final UsuarioServicio usuarioServicio;

    public AuthControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }
    
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponseDto> registrar(@RequestBody UsuarioRequestDto dto) {
        UsuarioResponseDto response = usuarioServicio.registrar(dto);
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }
    
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
