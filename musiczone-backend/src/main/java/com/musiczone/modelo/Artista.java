package com.musiczone.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "artistas")
public class Artista {

    @Id
    private String id;

    @Field("nombre")
    private String nombre;

    @Field("genero")
    private String genero;

    @Field("pais")
    private String pais;

    @Field("bio")
    private String bio;

    @Field("perfil_url")
    private String perfilUrl;

    private List<Album> albumes = new ArrayList<>();
    private List<Cancion> canciones = new ArrayList<>();

    public Artista() {}

    public Artista(String id, String nombre, String genero, String pais, String bio, String perfilUrl,
            List<Album> albumes, List<Cancion> canciones) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.pais = pais;
        this.bio = bio;
        this.perfilUrl = perfilUrl;
        this.albumes = albumes;
        this.canciones = canciones;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getPerfilUrl() { return perfilUrl; }
    public void setPerfilUrl(String perfilUrl) { this.perfilUrl = perfilUrl; }

    @JsonIgnore
    public List<Album> getAlbumes() { return albumes; }
    public void setAlbumes(List<Album> albumes) { this.albumes = albumes; }

    @JsonIgnore
    public List<Cancion> getCanciones() { return canciones; }
    public void setCanciones(List<Cancion> canciones) { this.canciones = canciones; }
}