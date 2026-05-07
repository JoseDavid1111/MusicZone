package com.musiczone.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

//En nuestra BD tenemos la tabla cancion_playlist la cual nace como la relacion muchos a muchos entre ellas
//Esta tabla tiene una llave compuesta formada por 2 ID´s, el JPA no sabe como hacer eso
//Entonces creamos esta clase que representa esa llave compuesta con ambos atributos

@Embeddable
public class CancionPlaylistId implements Serializable {

    /**
	 * Se coloca el serialVersion UID porque usamos Serializable indica que es Embeddable: llave compuesta
	 * Entonces pide un numero de version, solo es precaucion
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "id_playlist")
    private Long playlistId;

    @Column(name = "id_cancion")
    private Long cancionId;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CancionPlaylistId that = (CancionPlaylistId) o;
        return java.util.Objects.equals(playlistId, that.playlistId) &&
               java.util.Objects.equals(cancionId, that.cancionId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(playlistId, cancionId);
    }
    
    
    
	public CancionPlaylistId() {
	}

	public CancionPlaylistId(Long playlistId, Long cancionId) {
		super();
		this.playlistId = playlistId;
		this.cancionId = cancionId;
	}

	public Long getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(Long playlistId) {
		this.playlistId = playlistId;
	}

	public Long getCancionId() {
		return cancionId;
	}

	public void setCancionId(Long cancionId) {
		this.cancionId = cancionId;
	}
    
    
}
