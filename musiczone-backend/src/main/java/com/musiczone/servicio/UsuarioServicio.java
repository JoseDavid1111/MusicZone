package com.musiczone.servicio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.musiczone.dto.LoginRequestDto;
import com.musiczone.dto.LoginResponseDto;
import com.musiczone.dto.UsuarioRequestDto;
import com.musiczone.dto.UsuarioResponseDto;
import com.musiczone.modelo.Usuario;
import com.musiczone.repositorio.UsuarioRepositorio;
import com.musiczone.seguridad.JwtUtil;

@Service
public class UsuarioServicio implements IUsuarioServicio {
	
	@Autowired
	private JwtUtil jwtUtil;

    private final UsuarioRepositorio usuarioRepositorio;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        String identificador = dto.getNombreUsuario().trim();

        Usuario usuario = usuarioRepositorio
            .findByNombreUsuario(identificador)
            .or(() -> usuarioRepositorio.findByCorreo(identificador))
            .orElse(null);

        if (usuario == null || !usuario.getActive()) return null;

        if (!passwordEncoder.matches(dto.getPassword().trim(), usuario.getPassword())) {
            return null;
        }

        String token = jwtUtil.generarToken(usuario.getNombreUsuario());

        return new LoginResponseDto(
            usuario.getId(),
            usuario.getNombreUsuario(),
            usuario.getCorreo(),
            usuario.getActive(),
            token
        );
    }

    @Override
    public UsuarioResponseDto registrar(UsuarioRequestDto dto) {
        String nombreUsuario = dto.getNombreUsuario().trim();
        String correo = dto.getCorreo().trim().toLowerCase();
        String password = dto.getPassword().trim();

        if (usuarioRepositorio.existsByNombreUsuario(nombreUsuario)) return null;
        if (usuarioRepositorio.existsByCorreo(correo)) return null;

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setCorreo(correo);
        usuario.setActive(true);
        usuario.setFechaCreacion(LocalDateTime.now());

        Usuario guardado = usuarioRepositorio.save(usuario);
        return mapearUsuario(guardado);
    }

    @Override
    public List<UsuarioResponseDto> listarTodos() {
        return usuarioRepositorio.findAll()
            .stream()
            .map(this::mapearUsuario)
            .toList();
    }

    @Override
    public UsuarioResponseDto buscarUsuario(String id) {
        Usuario usuario = usuarioRepositorio.findById(id).orElse(null);
        if (usuario == null) return null;
        return mapearUsuario(usuario);
    }

    private UsuarioResponseDto mapearUsuario(Usuario usuario) {
        return new UsuarioResponseDto(
            usuario.getId(),
            usuario.getNombreUsuario(),
            usuario.getCorreo(),
            usuario.getActive(),
            usuario.getFechaCreacion()
        );
    }
}
