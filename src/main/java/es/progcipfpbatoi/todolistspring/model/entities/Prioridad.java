package es.progcipfpbatoi.todolistspring.model.entities;

// Valores posibles para indicar la importancia de una tarea.
public enum Prioridad {
    ALTA("Alta"),
    MITJANA("Mitjana"),
    BAIXA("Baixa");

    // Texto que se muestra en la web para que no salga el nombre interno del enum.
    private final String texto;

    Prioridad(String texto) {
        this.texto = texto;
    }

    // Texto que se mostrara luego en las vistas.
    public String getTexto() {
        return texto;
    }
}
