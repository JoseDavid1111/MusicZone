package com.musiczone.dto;

// DTO que representa una canción dentro de una playlist.
public class CancionPlaylistDto {

    // Identificador de la canción en MongoDB.
    private String idCancion;
    private String titulo;
    private String artista;
    private Integer posicion;
    private String urlAudio;

    public CancionPlaylistDto() {}

    public CancionPlaylistDto(String idCancion, String titulo, String artista, Integer posicion) {
        this(idCancion, titulo, artista, posicion, null);
    }

    public CancionPlaylistDto(String idCancion, String titulo, String artista, Integer posicion, String urlAudio) {
        this.idCancion = idCancion;
        this.titulo = titulo;
        this.artista = artista;
        this.posicion = posicion;
        this.urlAudio = urlAudio;
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
}
