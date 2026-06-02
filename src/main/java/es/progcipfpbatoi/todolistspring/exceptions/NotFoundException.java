package es.progcipfpbatoi.todolistspring.exceptions;

public class NotFoundException extends Exception {

    // Excepcio per a quan es busca una tasca que no esta guardada.
    public NotFoundException(String message) {
        super(message);
    }
}
