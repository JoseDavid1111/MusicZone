package com.musiczone.servicio;

import java.util.List;
import com.musiczone.dto.LoginRequestDto;
import com.musiczone.dto.LoginResponseDto;
import com.musiczone.dto.UsuarioRequestDto;
import com.musiczone.dto.UsuarioResponseDto;

// Se mantiene la misma estructura de interface + implementación
// Solo cambia el tipo del id de Long a String por el ObjectId de MongoDB
public interface IUsuarioServicio {
    // login genera un JWT en vez de solo validar credenciales
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    UsuarioResponseDto registrar(UsuarioRequestDto usuarioRequestDto);
    List<UsuarioResponseDto> listarTodos();

    // El tipo del id cambia de Long a String por el ObjectId de MongoDB
    UsuarioResponseDto buscarUsuario(String id);
}
