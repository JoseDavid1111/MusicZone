package com.musiczone.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PlaylistRequestDto {
    @NotBlank(message = "El nombre de la playlist es obligatorio")
    private String nombre;
    private String descripcion;
    @NotNull(message = "El usuario es obligatorio")
    private Long idUsuario;

    public PlaylistRequestDto() {}

    public PlaylistRequestDto(String nombre, String descripcion, Long idUsuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idUsuario = idUsuario;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
}
