package com.musiczone.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.musiczone.modelo.Usuario;

import java.util.Optional;

// Se reemplaza JpaRepository por MongoRepository
// El segundo parámetro cambia de Long a String por el ObjectId de MongoDB
public interface UsuarioRepositorio extends MongoRepository<Usuario, String> {

    // Optional se mantiene igual — si el usuario no existe retorna vacío en vez de null
    // Spring Data MongoDB soporta estos métodos derivados igual que JPA
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByCorreo(String correo);

    // Validaciones antes de registrar un usuario
    // Útil para evitar duplicados antes de intentar insertar
    // MongoDB lanzaría un error por el @Indexed(unique=true) del modelo,
    // pero es mejor validar antes y devolver un mensaje claro al cliente
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByCorreo(String correo);
}
