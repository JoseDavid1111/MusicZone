package com.musiczone.dto;

import jakarta.validation.constraints.NotNull;

public class AgregarCancionPlaylistDto {

    // Cambia de Long a String por el ObjectId de MongoDB
    @NotNull(message = "El id de la cancion es obligatorio")
    private String idCancion;
    private Integer posicion;

    public AgregarCancionPlaylistDto() {}

    public AgregarCancionPlaylistDto(String idCancion, Integer posicion) {
        this.idCancion = idCancion;
        this.posicion = posicion;
    }

    public String getIdCancion() { return idCancion; }
    public void setIdCancion(String idCancion) { this.idCancion = idCancion; }
    public Integer getPosicion() { return posicion; }
    public void setPosicion(Integer posicion) { this.posicion = posicion; }
}
