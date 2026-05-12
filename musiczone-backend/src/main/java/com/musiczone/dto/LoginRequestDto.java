package com.musiczone.dto;

import jakarta.validation.constraints.NotBlank;

// DTO para recibir las credenciales del usuario en el endpoint de login
// nombreUsuario acepta tanto el nombre de usuario como el correo
public class LoginRequestDto {

    @NotBlank(message = "El usuario o correo es obligatorio")
    private String nombreUsuario;

    @NotBlank(message = "La contrasena es obligatoria")
    private String password;

    public LoginRequestDto() {}

    public LoginRequestDto(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}