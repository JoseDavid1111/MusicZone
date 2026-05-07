package com.musiczone.dto;

import com.fasterxml.jackson.annotation.JsonInclude;


import java.time.LocalDateTime;
import java.util.List;


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
}
