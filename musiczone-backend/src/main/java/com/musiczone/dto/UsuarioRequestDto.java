package com.musiczone.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioRequestDto {
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = 50, message = "El nombre de usuario no puede superar 50 caracteres")
    private String nombreUsuario;

    @NotBlank(message = "La contrasena es obligatoria")
    @Size(min = 6, max = 100, message = "La contrasena debe tener entre 6 y 100 caracteres")
    private String password;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato valido")
    @Size(max = 100, message = "El correo no puede superar 100 caracteres")
    private String correo;

    public UsuarioRequestDto() {}

    public UsuarioRequestDto(String nombreUsuario, String password, String correo) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.correo = correo;
    }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}
