package com.musiczone.dto;

import java.time.LocalDateTime;

public class PlaylistResponseDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private Long idUsuario;
    private String nombreUsuario;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public PlaylistResponseDto() {}

    public PlaylistResponseDto(Long id, String nombre, String descripcion, Long idUsuario,
            String nombreUsuario, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
