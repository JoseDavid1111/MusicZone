package com.musiczone.dto;

import jakarta.validation.constraints.NotBlank;

// DTO para recibir los datos al crear o actualizar una playlist
// En JPA se recibía idUsuario (Long) para buscar el objeto Usuario por id
// En MongoDB se recibe el nombreUsuario directamente porque es la referencia que usa Playlist
public class PlaylistRequestDto {

    @NotBlank(message = "El nombre de la playlist es obligatorio")
    private String nombre;
    private String descripcion;

    // Cambia de idUsuario (Long) a nombreUsuario (String)
    // En MongoDB las playlists se asocian al usuario por nombre, no por id numérico
    @NotBlank(message = "El usuario es obligatorio")
    private String nombreUsuario;

    public PlaylistRequestDto() {}

    public PlaylistRequestDto(String nombre, String descripcion, String nombreUsuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
}
