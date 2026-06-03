package es.progcipfpbatoi.todolistspring.model.repositories;

import es.progcipfpbatoi.todolistspring.exceptions.NotFoundException;
import es.progcipfpbatoi.todolistspring.model.entities.Tarea;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class TareaRepository {

    // En esta rama las tareas se guardan en una lista mientras la aplicacion esta encendida.
    private ArrayList<Tarea> tareas;
    // Se usa para dar un codigo diferente a cada tarea nueva.
    private int siguienteCodigo;

    // Al arrancar la aplicacion se prepara la lista vacia y el primer codigo.
    public TareaRepository() {
        this.tareas = new ArrayList<>();
        this.siguienteCodigo = 1;
    }

    // Guarda la tarea en la lista y le asigna un codigo automatico.
    public void add(Tarea tarea) {
        tarea.setCodigo(siguienteCodigo);
        siguienteCodigo++;
        this.tareas.add(tarea);
    }

    // Busca una tarea por codigo recorriendo la lista.
    public Tarea get(int codTarea) throws NotFoundException {
        for (Tarea tarea : tareas) {
            if (tarea.getCodigo() == codTarea) {
                return tarea;
            }
        }

        throw new NotFoundException("La tasca amb codi " + codTarea + " no existeix");
    }

    // Devuelve todas las tareas que hay en memoria.
    public ArrayList<Tarea> findAll() {
        return tareas;
    }

    // Busca tareas solo por usuario.
    public ArrayList<Tarea> findAll(String usuario) {
        ArrayList<Tarea> tareasEncontradas = new ArrayList<>();

        // Busqueda sencilla por usuario, como se pide en el formulario.
        for (Tarea tarea : tareas) {
            if (tarea.getUsuario().equalsIgnoreCase(usuario)) {
                tareasEncontradas.add(tarea);
            }
        }

        return tareasEncontradas;
    }

    // Busca tareas aplicando los filtros que se hayan rellenado.
    public ArrayList<Tarea> findAll(String usuario, LocalDate fecha, Boolean realizada) {
        ArrayList<Tarea> tareasEncontradas = new ArrayList<>();

        for (Tarea tarea : tareas) {
            boolean coincideUsuario = usuario == null || usuario.isBlank()
                    || tarea.getUsuario().equalsIgnoreCase(usuario);
            boolean coincideFecha = fecha == null
                    || tarea.getFechaVencimiento().toLocalDate().equals(fecha);
            boolean coincideEstado = realizada == null || tarea.isRealizada() == realizada;

            if (coincideUsuario && coincideFecha && coincideEstado) {
                tareasEncontradas.add(tarea);
            }
        }

        return tareasEncontradas;
    }

    // Elimina una tarea de la lista si existe.
    public Tarea delete(int codTarea) throws NotFoundException {
        Tarea tarea = get(codTarea);
        tareas.remove(tarea);
        return tarea;
    }
}
