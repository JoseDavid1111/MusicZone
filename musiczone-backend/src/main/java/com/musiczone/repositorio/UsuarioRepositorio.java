package com.musiczone.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.musiczone.modelo.Usuario;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    //El optional se coloca por si el usuario no existe así regresa una lista vacia y no un error
	Optional<Usuario> findByNombreUsuario(String nombreUsuario);
	
	//Validaciones antes de registrar un usuario
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByCorreo(String correo);
}
