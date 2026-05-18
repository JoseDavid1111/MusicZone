package com.musiczone.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.musiczone.modelo.Usuario;

import java.util.Optional;

// Repositorio de usuarios en MongoDB.
public interface UsuarioRepositorio extends MongoRepository<Usuario, String> {

    // Métodos derivados por nombre para consultas frecuentes.
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByCorreo(String correo);

    // Validaciones antes de registrar un usuario
    // Útil para evitar duplicados antes de intentar insertar
    // MongoDB lanzaría un error por el @Indexed(unique=true) del modelo,
    // pero es mejor validar antes y devolver un mensaje claro al cliente
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByCorreo(String correo);
}
