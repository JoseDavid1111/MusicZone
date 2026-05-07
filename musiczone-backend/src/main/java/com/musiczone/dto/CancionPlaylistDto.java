package com.musiczone.dto;

public class CancionPlaylistDto {
    private Long idCancion;
    private String titulo;
    private String artista;
    private String album;
    private Integer posicion;

    public CancionPlaylistDto() {}

    public CancionPlaylistDto(Long idCancion, String titulo, String artista, 
            String album, Integer posicion) {
        this.idCancion = idCancion;
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.posicion = posicion;
    }

    public Long getIdCancion() { 
    	return idCancion; 
    }
    
    public void setIdCancion(Long idCancion) { 
    	this.idCancion = idCancion; 
    }
    
    public String getTitulo() { 
    	return titulo; 
    }
    
    public void setTitulo(String titulo) { 
    	this.titulo = titulo;
    }
    
    public String getArtista() { 
    	return artista; 
    }
    
    public void setArtista(String artista) { 
    	this.artista = artista; 
    }
    
    public String getAlbum() { 
    	return album; 
    }
    
    public void setAlbum(String album) { 
    	this.album = album; 
    }
    
    public Integer getPosicion() { 
    	return posicion; 
    }
    
    public void setPosicion(Integer posicion) { 
    	this.posicion = posicion; 
    }
}
