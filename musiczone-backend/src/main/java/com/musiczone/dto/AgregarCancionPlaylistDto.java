package com.musiczone.dto;

import jakarta.validation.constraints.NotNull;

public class AgregarCancionPlaylistDto {
    @NotNull(message = "El id de la cancion es obligatorio")
    private Long idCancion;
    private Integer posicion;

    public AgregarCancionPlaylistDto() {}

    public AgregarCancionPlaylistDto(Long idCancion, Integer posicion) {
        this.idCancion = idCancion;
        this.posicion = posicion;
    }

    public Long getIdCancion() { return idCancion; }
    public void setIdCancion(Long idCancion) { this.idCancion = idCancion; }
    public Integer getPosicion() { return posicion; }
    public void setPosicion(Integer posicion) { this.posicion = posicion; }
}
