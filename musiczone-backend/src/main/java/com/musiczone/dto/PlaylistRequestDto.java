package com.musiczone.dto;

import jakarta.validation.constraints.NotBlank;

// DTO para recibir los datos al crear o actualizar una playlist.
// La playlist se asocia al usuario mediante su nombre de usuario.
public class PlaylistRequestDto {

    @NotBlank(message = "El nombre de la playlist es obligatorio")
    private String nombre;
    private String descripcion;

    // Usuario propietario de la playlist.
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
