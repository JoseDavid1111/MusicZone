package com.musiczone.dto;


public class CancionResponseDto {
    private Long id;
    private String titulo;
    private String artista;
    private String album;
    private Integer duracionSegundos;
    private Integer numeroTrack;
    private String genero;
    private Integer yearLanzamiento;
    
    
	public CancionResponseDto(Long id, String titulo, String artista, String album, Integer duracionSegundos,
			Integer numeroTrack, String genero, Integer yearLanzamiento) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.artista = artista;
		this.album = album;
		this.duracionSegundos = duracionSegundos;
		this.numeroTrack = numeroTrack;
		this.genero = genero;
		this.yearLanzamiento = yearLanzamiento;
	}
	
	

	public CancionResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public Integer getDuracionSegundos() {
		return duracionSegundos;
	}


	public void setDuracionSegundos(Integer duracionSegundos) {
		this.duracionSegundos = duracionSegundos;
	}


	public Integer getNumeroTrack() {
		return numeroTrack;
	}


	public void setNumeroTrack(Integer numeroTrack) {
		this.numeroTrack = numeroTrack;
	}


	public String getGenero() {
		return genero;
	}


	public void setGenero(String genero) {
		this.genero = genero;
	}


	public Integer getYearLanzamiento() {
		return yearLanzamiento;
	}


	public void setYearLanzamiento(Integer yearLanzamiento) {
		this.yearLanzamiento = yearLanzamiento;
	}
    
	
    
}

