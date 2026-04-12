package com.musiczone.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Cancion.TABLA_NAME)
public class Cancion {
    public static final String TABLA_NAME = "cancion";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cancion")
    private Long id;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_artista", nullable = false)
    @JsonIgnoreProperties({"albumes", "canciones"})
    private Artista artista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_album", nullable =true) //el campo puede ser nulo, las canciones que son singles, existen sin albums
    @JsonIgnoreProperties({"artista", "canciones"})
    private Album album;

    @Column(name = "duracion_segundos")
    private Integer duracionSegundos;

    @Column(name = "numero_track")
    private Integer numeroTrack;

    @Column(name = "genero", length = 80)
    private String genero;

    @Column(name = "year_lanzamiento", columnDefinition = "YEAR")
    private Integer yearLanzamiento;

    @OneToMany(mappedBy = "cancion")
    @JsonIgnoreProperties({"cancion", "playlist"})
    private List<CancionPlaylist> cancionesPlaylist = new ArrayList<>();

	public Cancion(Long id, String titulo, Artista artista, Album album, Integer duracionSegundos, Integer numeroTrack,
			String genero, Integer yearLanzamiento, List<CancionPlaylist> cancionesPlaylist) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.artista = artista;
		this.album = album;
		this.duracionSegundos = duracionSegundos;
		this.numeroTrack = numeroTrack;
		this.genero = genero;
		this.yearLanzamiento = yearLanzamiento;
		this.cancionesPlaylist = cancionesPlaylist;
	}
	
	

	public Cancion() {
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

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
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

	public List<CancionPlaylist> getCancionesPlaylist() {
		return cancionesPlaylist;
	}

	public void setCancionesPlaylist(List<CancionPlaylist> cancionesPlaylist) {
		this.cancionesPlaylist = cancionesPlaylist;
	}
    
    

    
}

