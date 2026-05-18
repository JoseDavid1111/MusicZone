package com.musiczone.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

// Las playlists se consultan por separado usando el nombre de usuario como referencia.
// Esto evita documentos de usuario demasiado grandes con listas anidadas.
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;

    // Garantiza que no haya dos usuarios con el mismo nombre.
    @Indexed(unique = true)
    @Field("nombre_usuario")
    private String nombreUsuario;

    // El password nunca viaja en las respuestas JSON por seguridad.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Field("password")
    private String password;

    @Indexed(unique = true)
    @Field("correo")
    private String correo;

    @Field("active")
    private Boolean active;

    @Field("fecha_creacion")
    private LocalDateTime fechaCreacion;

    public Usuario() {}

    public Usuario(String id, String nombreUsuario, String password, String correo,
            Boolean active, LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.correo = correo;
        this.active = active;
        this.fechaCreacion = fechaCreacion;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
