package es.progcipfpbatoi.todolistspring.model.entities;

import java.time.LocalDateTime;

public class Tarea {

    private int codigo;

    private String nombre;

    private String descripcion;

    private LocalDateTime creadoEn;

    public Tarea(int codigo, String nombre, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creadoEn = LocalDateTime.now();
    }

    public int getCodigo() {
        return codigo;
    }
}
