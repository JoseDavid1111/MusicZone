package com.musiczone.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity

@Table(name = CancionPlaylist.TABLA_NAME)
public class CancionPlaylist {
    public static final String TABLA_NAME = "cancion_playlist";

    @EmbeddedId
    private CancionPlaylistId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playlistId")
    @JoinColumn(name = "id_playlist")
    @JsonIgnoreProperties({"usuario", "cancionesPlaylist"})
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cancionId")
    @JoinColumn(name = "id_cancion")
    @JsonIgnoreProperties({"artista", "album", "cancionesPlaylist"})
    private Cancion cancion;

    @Column(name = "posicion")
    private Integer posicion;

    @Column(name = "fecha_agregada", nullable = false, updatable = false)
    private LocalDateTime fechaAgregada;

	public CancionPlaylist(CancionPlaylistId id, Playlist playlist, Cancion cancion, Integer posicion,
			LocalDateTime fechaAgregada) {
		super();
		this.id = id;
		this.playlist = playlist;
		this.cancion = cancion;
		this.posicion = posicion;
		this.fechaAgregada = fechaAgregada;
	}

	public CancionPlaylistId getId() {
		return id;
	}

	public void setId(CancionPlaylistId id) {
		this.id = id;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public Cancion getCancion() {
		return cancion;
	}

	public void setCancion(Cancion cancion) {
		this.cancion = cancion;
	}

	public Integer getPosicion() {
		return posicion;
	}

	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	public LocalDateTime getFechaAgregada() {
		return fechaAgregada;
	}

	public void setFechaAgregada(LocalDateTime fechaAgregada) {
		this.fechaAgregada = fechaAgregada;
	}

	public static String getTablaName() {
		return TABLA_NAME;
	}

	public CancionPlaylist() {
	}
    
    
    
}


