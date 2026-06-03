package es.progcipfpbatoi.todolistspring.exceptions;

// Excepcion propia para cuando se busca una tarea que no existe.
public class NotFoundException extends Exception {

    // Guardo el mensaje para indicar que codigo no se ha encontrado.
    public NotFoundException(String message) {
        super(message);
    }
}
