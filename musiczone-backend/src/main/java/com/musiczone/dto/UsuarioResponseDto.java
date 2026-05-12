package com.musiczone.dto;

import java.time.LocalDateTime;

// DTO para devolver los datos del usuario en las respuestas de la API
// El password nunca se incluye aquí por seguridad
public class UsuarioResponseDto {

    // Cambia de Long a String por el ObjectId de MongoDB
    private String id;
    private String nombreUsuario;
    private String correo;
    private Boolean active;
    private LocalDateTime fechaCreacion;

    public UsuarioResponseDto() {}

    public UsuarioResponseDto(String id, String nombreUsuario, String correo,
            Boolean active, LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.active = active;
        this.fechaCreacion = fechaCreacion;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
