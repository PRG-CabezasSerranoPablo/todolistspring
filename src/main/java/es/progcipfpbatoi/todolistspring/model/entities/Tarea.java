package es.progcipfpbatoi.todolistspring.model.entities;

import java.time.LocalDateTime;

// Clase que representa una tarea dentro de la aplicacion.
public class Tarea {

    private int codigo;
    private String descripcion;
    private String usuario;
    private LocalDateTime fechaVencimiento;
    private Prioridad prioridad;
    private String categoria;
    private boolean realizada;

    // Constructor vacio necesario para que Spring pueda crear objetos si lo necesita.
    public Tarea() {
    }

    // Constructor principal con todos los datos que tiene una tarea.
    public Tarea(int codigo, String descripcion, String usuario, LocalDateTime fechaVencimiento,
                 Prioridad prioridad, String categoria, boolean realizada) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.fechaVencimiento = fechaVencimiento;
        this.prioridad = prioridad;
        this.categoria = categoria;
        this.realizada = realizada;
    }

    // Constructor sencillo para mantener la plantilla inicial funcionando.
    public Tarea(int codigo, String usuario, String descripcion) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.fechaVencimiento = LocalDateTime.now();
        this.prioridad = Prioridad.MITJANA;
        this.categoria = "";
        this.realizada = false;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isRealizada() {
        return realizada;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }
}
