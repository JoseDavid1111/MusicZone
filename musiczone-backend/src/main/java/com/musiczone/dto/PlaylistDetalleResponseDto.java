package com.musiczone.dto;

import java.time.LocalDateTime;
import java.util.List;

// DTO para la respuesta detallada de una playlist incluyendo sus canciones
// En JPA tenía idUsuario (Long) y nombreUsuario por separado porque venían de un JOIN
// En MongoDB solo se guarda el nombreUsuario como referencia, no hay idUsuario separado
public class PlaylistDetalleResponseDto {

    // Cambia de Long a String por el ObjectId de MongoDB
    private String id;
    private String nombre;
    private String descripcion;
    // Se elimina idUsuario — en MongoDB la playlist solo referencia al usuario por nombre
    private String nombreUsuario;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private List<CancionPlaylistDto> canciones;

    public PlaylistDetalleResponseDto() {}

    public PlaylistDetalleResponseDto(String id, String nombre, String descripcion,
            String nombreUsuario, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion,
            List<CancionPlaylistDto> canciones) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nombreUsuario = nombreUsuario;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.canciones = canciones;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
    public List<CancionPlaylistDto> getCanciones() { return canciones; }
    public void setCanciones(List<CancionPlaylistDto> canciones) { this.canciones = canciones; }
}
