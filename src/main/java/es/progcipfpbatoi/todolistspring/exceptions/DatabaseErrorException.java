package es.progcipfpbatoi.todolistspring.exceptions;

public class DatabaseErrorException extends Exception {

    // Excepcio per a avisar que hi ha hagut un problema amb la base de dades.
    public DatabaseErrorException(String message) {
        super(message);
    }
}
