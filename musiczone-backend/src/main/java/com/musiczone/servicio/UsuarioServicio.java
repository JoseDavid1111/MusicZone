package com.musiczone.servicio;

import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.musiczone.dto.LoginRequestDto;
import com.musiczone.dto.LoginResponseDto;
import com.musiczone.dto.UsuarioRequestDto;
import com.musiczone.dto.UsuarioResponseDto;
import com.musiczone.modelo.Usuario;
import com.musiczone.repositorio.UsuarioRepositorio;

@Service
@Transactional
public class UsuarioServicio implements IUsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, 
            BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        Usuario usuario = usuarioRepositorio
            .findByNombreUsuario(dto.getNombreUsuario().trim())
            .orElse(null);

        if (usuario == null || !usuario.getActive()) return null;

        // Comparación limpia
        if (!passwordEncoder.matches(dto.getPassword().trim(), usuario.getPassword())) {
            return null;
        }

        return new LoginResponseDto(
            usuario.getId(),
            usuario.getNombreUsuario(),
            usuario.getCorreo(),
            usuario.getActive()
        );
    }

    @Override
    public UsuarioResponseDto registrar(UsuarioRequestDto dto) {
        if (usuarioRepositorio.existsByNombreUsuario(dto.getNombreUsuario())) return null;
        if (usuarioRepositorio.existsByCorreo(dto.getCorreo())) return null;

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setCorreo(dto.getCorreo());
        usuario.setActive(true);
        
        // ESTA LÍNEA ES VITAL PARA EVITAR EL ERROR 500
        usuario.setFechaCreacion(java.time.LocalDateTime.now()); 

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
    public UsuarioResponseDto buscarUsuario(Long id) {
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
