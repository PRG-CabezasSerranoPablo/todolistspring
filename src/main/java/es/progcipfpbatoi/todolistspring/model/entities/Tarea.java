package es.progcipfpbatoi.todolistspring.model.entities;

import java.time.LocalDateTime;

// Clase que representa una tarea dentro de la aplicacion.
public class Tarea {

    private int codigo;
    private String descripcion;
    private String usuario;
    // En Perst guardo la fecha como texto porque LocalDateTime da problemas al persistirlo.
    private String fechaVencimiento;
    private Prioridad prioridad;
    private String categoria;
    private boolean realizada;

    // Constructor vacio necesario para que algunas herramientas puedan crear el objeto.
    public Tarea() {
    }

    // Constructor principal para crear una tarea con todos sus datos.
    public Tarea(int codigo, String descripcion, String usuario, LocalDateTime fechaVencimiento,
                 Prioridad prioridad, String categoria, boolean realizada) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.fechaVencimiento = fechaVencimiento.toString();
        this.prioridad = prioridad;
        this.categoria = categoria;
        this.realizada = realizada;
    }

    // Constructor sencillo para mantener la plantilla inicial funcionando con pocos datos.
    public Tarea(int codigo, String usuario, String descripcion) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.fechaVencimiento = LocalDateTime.now().toString();
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
        // Lo convierto de texto a LocalDateTime para que el resto del codigo lo use normal.
        return LocalDateTime.parse(fechaVencimiento);
    }

    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        // Lo guardo como texto para que Perst pueda persistirlo sin fallar.
        this.fechaVencimiento = fechaVencimiento.toString();
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
