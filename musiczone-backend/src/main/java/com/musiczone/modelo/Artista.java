package com.musiczone.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Artista.TABLA_NAME)
public class Artista {
    public static final String TABLA_NAME = "artista";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artista")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "genero", length = 80)
    private String genero;

    @Column(name = "pais", length = 80)
    private String pais;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "perfil_url", length = 500)
    private String perfilUrl;

    @OneToMany(mappedBy = "artista")
    @JsonIgnoreProperties("artista")
    private List<Album> albumes = new ArrayList<>();

    @OneToMany(mappedBy = "artista")
    @JsonIgnoreProperties("artista")
    private List<Cancion> canciones = new ArrayList<>();

	public Artista(Long id, String nombre, String genero, String pais, String bio, String perfilUrl,
			List<Album> albumes, List<Cancion> canciones) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.genero = genero;
		this.pais = pais;
		this.bio = bio;
		this.perfilUrl = perfilUrl;
		this.albumes = albumes;
		this.canciones = canciones;
	}
	
	

	public Artista() {
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getPerfilUrl() {
		return perfilUrl;
	}

	public void setPerfilUrl(String perfilUrl) {
		this.perfilUrl = perfilUrl;
	}

	public List<Album> getAlbumes() {
		return albumes;
	}

	public void setAlbumes(List<Album> albumes) {
		this.albumes = albumes;
	}

	public List<Cancion> getCanciones() {
		return canciones;
	}

	public void setCanciones(List<Cancion> canciones) {
		this.canciones = canciones;
	}
    
    


}

