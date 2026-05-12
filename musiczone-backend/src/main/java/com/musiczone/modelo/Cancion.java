package com.musiczone.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "canciones")
public class Cancion {

    @Id
    private String id;

    @Field("titulo")
    private String titulo;

    @Field("artista")
    private Artista artista;

    @Field("album")
    private Album album;

    @Field("duracion_segundos")
    private Integer duracionSegundos;

    @Field("numero_track")
    private Integer numeroTrack;

    @Field("genero")
    private String genero;

    @Field("year_lanzamiento")
    private Integer yearLanzamiento;

    @Field("url_audio")
    private String urlAudio;

    public Cancion() {}

    public Cancion(String id, String titulo, Artista artista, Album album, Integer duracionSegundos,
            Integer numeroTrack, String genero, Integer yearLanzamiento, String urlAudio) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.duracionSegundos = duracionSegundos;
        this.numeroTrack = numeroTrack;
        this.genero = genero;
        this.yearLanzamiento = yearLanzamiento;
        this.urlAudio = urlAudio;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Artista getArtista() { return artista; }
    public void setArtista(Artista artista) { this.artista = artista; }

    public Album getAlbum() { return album; }
    public void setAlbum(Album album) { this.album = album; }

    public Integer getDuracionSegundos() { return duracionSegundos; }
    public void setDuracionSegundos(Integer duracionSegundos) { this.duracionSegundos = duracionSegundos; }

    public Integer getNumeroTrack() { return numeroTrack; }
    public void setNumeroTrack(Integer numeroTrack) { this.numeroTrack = numeroTrack; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public Integer getYearLanzamiento() { return yearLanzamiento; }
    public void setYearLanzamiento(Integer yearLanzamiento) { this.yearLanzamiento = yearLanzamiento; }

    public String getUrlAudio() { return urlAudio; }
    public void setUrlAudio(String urlAudio) { this.urlAudio = urlAudio; }
}