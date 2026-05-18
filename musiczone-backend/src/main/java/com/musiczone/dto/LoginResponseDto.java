package com.musiczone.dto;

// DTO para la respuesta del login.
// Incluye el token JWT usado por el frontend en las rutas protegidas.
public class LoginResponseDto {

    private String id;
    private String nombreUsuario;
    private String correo;
    private Boolean active;
    private String token;

    public LoginResponseDto() {}

    public LoginResponseDto(String id, String nombreUsuario, String correo, Boolean active, String token) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.active = active;
        this.token = token;
    }


	public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
