package com.musiczone.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PlaylistDetalleResponseDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private Long idUsuario;
    private String nombreUsuario;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private List<CancionPlaylistDto> canciones;

    public PlaylistDetalleResponseDto() {}

    public PlaylistDetalleResponseDto(Long id, String nombre, String descripcion, Long idUsuario,
            String nombreUsuario, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion,
            List<CancionPlaylistDto> canciones) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.canciones = canciones;
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
    public List<CancionPlaylistDto> getCanciones() { return canciones; }
    public void setCanciones(List<CancionPlaylistDto> canciones) { this.canciones = canciones; }
}
