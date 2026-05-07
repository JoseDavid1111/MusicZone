package com.musiczone.dto;

public class LoginResponseDto {
    private Long id;
    private String nombreUsuario;
    private String correo;
    private Boolean active;

    public LoginResponseDto() {}

    public LoginResponseDto(Long id, String nombreUsuario, String correo, Boolean active) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.active = active;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}