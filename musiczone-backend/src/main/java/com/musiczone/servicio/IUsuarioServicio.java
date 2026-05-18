package com.musiczone.servicio;

import java.util.List;
import com.musiczone.dto.LoginRequestDto;
import com.musiczone.dto.LoginResponseDto;
import com.musiczone.dto.UsuarioRequestDto;
import com.musiczone.dto.UsuarioResponseDto;

// Contrato de operaciones relacionadas con usuarios.
public interface IUsuarioServicio {
    // Login con generación de JWT para las rutas protegidas.
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    UsuarioResponseDto registrar(UsuarioRequestDto usuarioRequestDto);
    List<UsuarioResponseDto> listarTodos();

    // Busca un usuario por su identificador.
    UsuarioResponseDto buscarUsuario(String id);
}
