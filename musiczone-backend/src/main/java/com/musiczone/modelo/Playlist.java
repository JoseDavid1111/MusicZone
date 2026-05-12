package com.musiczone.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// En MongoDB no existe la tabla intermedia cancion_playlist
// Las canciones de una playlist se guardan como array embebido dentro del documento
// Esto elimina la necesidad de joins y hace las consultas más rápidas
@Document(collection = "playlists")
public class Playlist {

    @Id
    private String id;

    @Field("nombre")
    private String nombre;

    // En JPA se usaba @ManyToOne con un objeto Usuario completo
    // En MongoDB guardamos solo el nombre de usuario como referencia ligera
    // evitando documentos anidados innecesariamente grandes
    @Field("usuario")
    private String usuario;

    @Field("descripcion")
    private String descripcion;

    @Field("fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Field("fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    // Reemplaza la tabla intermedia cancion_playlist
    // Cada elemento del array tiene: idCancion, titulo, artista, posicion, fechaAgregada
    @Field("canciones")
    private List<CancionPlaylist> canciones = new ArrayList<>();

    public Playlist() {}

    public Playlist(String id, String nombre, String usuario, String descripcion,
            LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion, List<CancionPlaylist> canciones) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.canciones = canciones;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public List<CancionPlaylist> getCanciones() { return canciones; }
    public void setCanciones(List<CancionPlaylist> canciones) { this.canciones = canciones; }
}