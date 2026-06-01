package es.progcipfpbatoi.todolistspring.model.entities;

public enum Prioridad {
    ALTA("Alta"),
    MITJANA("Mitjana"),
    BAIXA("Baixa");

    private final String texto;

    Prioridad(String texto) {
        this.texto = texto;
    }

    // Texto que se mostrara luego en las vistas.
    public String getTexto() {
        return texto;
    }
}
