package com.musiczone.dto;

public class ArtistaResponseDto {
    private Long id;
    private String nombre;
    private String genero;
    private String pais;
    private String bio;
    private String perfilUrl;

    public ArtistaResponseDto() {}

    public ArtistaResponseDto(Long id, String nombre, String genero, 
            String pais, String bio, String perfilUrl) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.pais = pais;
        this.bio = bio;
        this.perfilUrl = perfilUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
}
