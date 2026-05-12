package com.musiczone.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "albumes")
public class Album {

    @Id
    private String id;

    @Field("titulo")
    private String titulo;

    @Field("artista")
    private Artista artista;

    @Field("year_lanzamiento")
    private Integer yearLanzamiento;

    @Field("portada_url")
    private String portadaUrl;

    private List<Cancion> canciones = new ArrayList<>();

    public Album() {}

    public Album(String id, String titulo, Artista artista, Integer yearLanzamiento, String portadaUrl, List<Cancion> canciones) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.yearLanzamiento = yearLanzamiento;
        this.portadaUrl = portadaUrl;
        this.canciones = canciones;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Artista getArtista() { return artista; }
    public void setArtista(Artista artista) { this.artista = artista; }

    public Integer getYearLanzamiento() { return yearLanzamiento; }
    public void setYearLanzamiento(Integer yearLanzamiento) { this.yearLanzamiento = yearLanzamiento; }

    public String getPortadaUrl() { return portadaUrl; }
    public void setPortadaUrl(String portadaUrl) { this.portadaUrl = portadaUrl; }

    public List<Cancion> getCanciones() { return canciones; }
    public void setCanciones(List<Cancion> canciones) { this.canciones = canciones; }
}
