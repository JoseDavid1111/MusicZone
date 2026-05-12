package com.musiczone.dto;

// DTO que se usa para devolver datos de la canción en las respuestas de la API
// Evita exponer directamente el modelo y permite controlar qué campos se envían
public class CancionResponseDto {

    // Cambia de Long a String por el ObjectId de MongoDB
    private String id;
    private String titulo;
    private String artista;
    private String album;
    private Integer duracionSegundos;
    private Integer numeroTrack;
    private String genero;
    private Integer yearLanzamiento;
    // Campo nuevo — URL del archivo MP3 en Supabase Storage para reproducción
    private String urlAudio;

    public CancionResponseDto() {}

    public CancionResponseDto(String id, String titulo, String artista, String album,
            Integer duracionSegundos, Integer numeroTrack, String genero,
            Integer yearLanzamiento, String urlAudio) {
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

    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }

    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = album; }

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