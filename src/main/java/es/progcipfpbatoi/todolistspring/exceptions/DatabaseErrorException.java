package es.progcipfpbatoi.todolistspring.exceptions;

// Excepcion propia para controlar errores relacionados con guardar o leer datos.
public class DatabaseErrorException extends Exception {

    // Guardo el mensaje para poder ensenarlo luego en una vista.
    public DatabaseErrorException(String message) {
        super(message);
    }
}
