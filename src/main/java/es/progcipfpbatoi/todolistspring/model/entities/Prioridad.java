package es.progcipfpbatoi.todolistspring.model.entities;

// Enum para limitar las prioridades que puede tener una tarea.
public enum Prioridad {
    ALTA("Alta"),
    MITJANA("Mitjana"),
    BAIXA("Baixa");

    private final String texto;

    // Cada prioridad guarda tambien el texto que se ensena en la web.
    Prioridad(String texto) {
        this.texto = texto;
    }

    // Texto que se mostrara luego en las vistas de Thymeleaf.
    public String getTexto() {
        return texto;
    }
}
