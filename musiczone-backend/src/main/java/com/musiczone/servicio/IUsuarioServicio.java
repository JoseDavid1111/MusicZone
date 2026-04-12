package com.musiczone.servicio;

import java.util.List;
import com.musiczone.dto.LoginRequestDto;
import com.musiczone.dto.LoginResponseDto;
import com.musiczone.dto.UsuarioRequestDto;
import com.musiczone.dto.UsuarioResponseDto;

public interface IUsuarioServicio {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    UsuarioResponseDto registrar(UsuarioRequestDto usuarioRequestDto);
    List<UsuarioResponseDto> listarTodos();
    UsuarioResponseDto buscarUsuario(Long id);
}
