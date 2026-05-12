package com.musiczone.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;

// DTO para respuestas de error estandarizadas en la API
// @JsonInclude(NON_NULL) evita que los campos null aparezcan en el JSON de respuesta
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {

    private int codigo;
    private String mensaje;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String path;
    private List<String> detalles;

    public ErrorResponseDto(int codigo, String mensaje, String path) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }

    public ErrorResponseDto(int codigo, String mensaje, List<String> detalles, String path) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.timestamp = LocalDateTime.now();
        this.path = path;
        this.detalles = detalles;
    }

    // Getters necesarios para que Jackson pueda serializar los campos en el JSON
    public int getCodigo() { return codigo; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getPath() { return path; }
    public List<String> getDetalles() { return detalles; }
}
