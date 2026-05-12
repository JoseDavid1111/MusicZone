package com.musiczone.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

// Wrapper genérico para estandarizar todas las respuestas de la API
// T puede ser cualquier objeto: CancionResponseDto, List<PlaylistResponseDto>, etc.
// @JsonInclude(NON_NULL) evita que campos null aparezcan en el JSON de respuesta
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespuestaApi<T> {

    private boolean exito;
    private String mensaje;
    private T datos;
    private int codigo;
    private LocalDateTime timestamp;

    public RespuestaApi() {
        this.timestamp = LocalDateTime.now();
    }

    public RespuestaApi(boolean exito, String mensaje) {
        this();
        this.exito = exito;
        this.mensaje = mensaje;
    }

    public RespuestaApi(boolean exito, String mensaje, int codigo) {
        this();
        this.exito = exito;
        this.mensaje = mensaje;
        this.codigo = codigo;
    }

    public RespuestaApi(boolean exito, String mensaje, T datos, int codigo) {
        this();
        this.exito = exito;
        this.mensaje = mensaje;
        this.datos = datos;
        this.codigo = codigo;
    }

    public boolean isExito() { return exito; }
    public void setExito(boolean exito) { this.exito = exito; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public T getDatos() { return datos; }
    public void setDatos(T datos) { this.datos = datos; }
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}

