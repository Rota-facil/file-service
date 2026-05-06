package com.rota.facil.file_service.domain.exceptions;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException() {
        super("Arquivo não foi encontado");
    }
}
