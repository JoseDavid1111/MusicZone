package com.musiczone.modelo;

import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
// Representa una canción guardada dentro del arreglo de canciones de una playlist.
public class CancionPlaylist {

    @Field("id_cancion")
    private String idCancion;

    @Field("titulo")
    private String titulo;

    @Field("artista")
    private String artista;

    @Field("posicion")
    private Integer posicion;

    @Field("url_audio")
    private String urlAudio;

    @Field("fecha_agregada")
    private LocalDateTime fechaAgregada;

    public CancionPlaylist() {}

    public CancionPlaylist(String idCancion, String titulo, String artista, Integer posicion, LocalDateTime fechaAgregada) {
        this(idCancion, titulo, artista, posicion, null, fechaAgregada);
    }

    public CancionPlaylist(String idCancion, String titulo, String artista, Integer posicion, String urlAudio, LocalDateTime fechaAgregada) {
        this.idCancion = idCancion;
        this.titulo = titulo;
        this.artista = artista;
        this.posicion = posicion;
        this.urlAudio = urlAudio;
        this.fechaAgregada = fechaAgregada;
    }

    public String getIdCancion() { return idCancion; }
    public void setIdCancion(String idCancion) { this.idCancion = idCancion; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }

    public Integer getPosicion() { return posicion; }
    public void setPosicion(Integer posicion) { this.posicion = posicion; }

    public String getUrlAudio() { return urlAudio; }
    public void setUrlAudio(String urlAudio) { this.urlAudio = urlAudio; }

    public LocalDateTime getFechaAgregada() { return fechaAgregada; }
    public void setFechaAgregada(LocalDateTime fechaAgregada) { this.fechaAgregada = fechaAgregada; }
}
