package es.progcipfpbatoi.todolistspring.model.repositories;

import es.progcipfpbatoi.todolistspring.exceptions.NotFoundException;
import es.progcipfpbatoi.todolistspring.model.entities.Tarea;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class TareaRepository {

    private ArrayList<Tarea> tareas;
    private int siguienteCodigo;

    public TareaRepository() {
        this.tareas = new ArrayList<>();
        this.siguienteCodigo = 1;
    }

    public void add(Tarea tarea) {
        tarea.setCodigo(siguienteCodigo);
        siguienteCodigo++;
        this.tareas.add(tarea);
    }

    public Tarea get(int codTarea) throws NotFoundException {
        for (Tarea tarea : tareas) {
            if (tarea.getCodigo() == codTarea) {
                return tarea;
            }
        }

        throw new NotFoundException("La tasca amb codi " + codTarea + " no existeix");
    }

    public ArrayList<Tarea> findAll() {
        return tareas;
    }

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

    public Tarea delete(int codTarea) throws NotFoundException {
        Tarea tarea = get(codTarea);
        tareas.remove(tarea);
        return tarea;
    }
}
