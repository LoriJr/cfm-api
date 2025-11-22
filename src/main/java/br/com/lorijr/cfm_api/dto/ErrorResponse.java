package br.com.lorijr.cfm_api.dto;


import java.time.LocalDateTime;

public record ErrorResponse (
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path)
{}
