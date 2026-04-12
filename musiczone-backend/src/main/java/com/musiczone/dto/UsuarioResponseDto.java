package com.musiczone.dto;

import java.time.LocalDateTime;

public class UsuarioResponseDto {
    private Long id;
    private String nombreUsuario;
    private String correo;
    private Boolean active;
    private LocalDateTime fechaCreacion;

    public UsuarioResponseDto() {}

    public UsuarioResponseDto(Long id, String nombreUsuario, String correo, 
            Boolean active, LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.active = active;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
