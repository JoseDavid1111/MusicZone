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
@Table(name = Album.TABLA_NAME)
public class Album {
    public static final String TABLA_NAME = "album";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_album")
    private Long id;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_artista", nullable = false)
    @JsonIgnoreProperties({"albumes", "canciones"})
    private Artista artista;

    @Column(name = "year_lanzamiento", columnDefinition = "YEAR") 
    //En la BD esta como year acá lo manejamos como INTEGER, por lo que se usa columnDefinition, asi solo dejo especificado para evitar errores
    private Integer yearLanzamiento;

    @Column(name = "portada_url", length = 500)
    private String portadaUrl;

    @OneToMany(mappedBy = "album")
    @JsonIgnoreProperties("album")
    //le dice a Lombok que no incluya esa lista al generar el toString(), evitando que recorra toda la lista innecesariamente.
    private List<Cancion> canciones = new ArrayList<>();
    
    
    
	public Album() {
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

	public Integer getYearLanzamiento() {
		return yearLanzamiento;
	}

	public void setYearLanzamiento(Integer yearLanzamiento) {
		this.yearLanzamiento = yearLanzamiento;
	}

	public String getPortadaUrl() {
		return portadaUrl;
	}

	public void setPortadaUrl(String portadaUrl) {
		this.portadaUrl = portadaUrl;
	}

	public List<Cancion> getCanciones() {
		return canciones;
	}

	public void setCanciones(List<Cancion> canciones) {
		this.canciones = canciones;
	}

	public Album(Long id, String titulo, Artista artista, Integer yearLanzamiento, String portadaUrl,
			List<Cancion> canciones) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.artista = artista;
		this.yearLanzamiento = yearLanzamiento;
		this.portadaUrl = portadaUrl;
		this.canciones = canciones;
	}
    
    
}

