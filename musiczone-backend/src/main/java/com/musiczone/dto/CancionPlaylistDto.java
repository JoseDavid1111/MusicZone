package com.musiczone.dto;

// DTO que representa una canción dentro de una playlist
// En JPA incluía album porque venía de la tabla cancion_playlist con joins
// En MongoDB el album se omite porque CancionPlaylist solo guarda titulo y artista embebidos
public class CancionPlaylistDto {

    // Cambia de Long a String por el ObjectId de MongoDB
    private String idCancion;
    private String titulo;
    private String artista;
    private Integer posicion;

    public CancionPlaylistDto() {}

    public CancionPlaylistDto(String idCancion, String titulo, String artista, Integer posicion) {
        this.idCancion = idCancion;
        this.titulo = titulo;
        this.artista = artista;
        this.posicion = posicion;
    }

    public String getIdCancion() { return idCancion; }
    public void setIdCancion(String idCancion) { this.idCancion = idCancion; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }

    public Integer getPosicion() { return posicion; }
    public void setPosicion(Integer posicion) { this.posicion = posicion; }
}
