package es.progcipfpbatoi.todolistspring.exceptions;

// Excepcion propia para avisar cuando no se encuentra una tarea.
public class NotFoundException extends Exception {

    // Excepcio per a quan es busca una tasca que no esta guardada.
    public NotFoundException(String message) {
        super(message);
    }
}
