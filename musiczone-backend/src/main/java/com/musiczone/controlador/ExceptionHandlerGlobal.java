package com.musiczone.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.musiczone.dto.ErrorResponseDto;
import java.util.ArrayList;
import java.util.List;

// Manejador global de excepciones — intercepta errores de toda la API
// y los devuelve en formato estandarizado con ErrorResponseDto
// Evita que Spring devuelva stacktraces crudos al cliente
@ControllerAdvice
public class ExceptionHandlerGlobal {

    // Captura errores de validación de @Valid en los RequestBody
    // Por ejemplo: campos obligatorios vacíos o formatos incorrectos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> manejarValidacion(
            MethodArgumentNotValidException ex, WebRequest request) {
        List<String> detalles = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            detalles.add(error.getField() + ": " + error.getDefaultMessage())
        );
        ErrorResponseDto error = new ErrorResponseDto(
            HttpStatus.BAD_REQUEST.value(),
            "Error de validacion en los datos enviados",
            detalles,
            request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Captura argumentos inválidos lanzados manualmente con throw new IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> manejarIllegalArgument(
            IllegalArgumentException ex, WebRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Captura cualquier excepción no manejada por los handlers anteriores
    // Es el último recurso para evitar que el servidor devuelva un 500 sin contexto
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> manejarExcepcionGeneral(
            Exception ex, WebRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Error interno del servidor: " + ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}