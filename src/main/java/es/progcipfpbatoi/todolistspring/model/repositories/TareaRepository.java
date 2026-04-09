package es.progcipfpbatoi.todolistspring.model.repositories;

import es.progcipfpbatoi.todolistspring.exceptions.NotFoundException;
import es.progcipfpbatoi.todolistspring.model.entities.Tarea;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class TareaRepository {

    private ArrayList<Tarea> tareas;

    public TareaRepository() {
        this.tareas = new ArrayList<>();
    }

    /**
     * Añade la Tarea recibida como argumento a la base de datos en memoria
     * @param tarea
     */
    public void add(Tarea tarea) {
        this.tareas.add(tarea);
    }

    /**
     * Obtiene la Tarea con codigo @codTarea. En caso de que no la encuentre devolverá una excepción
     * @NotFoundException
     *
     * @param codTarea
     */
    public Tarea get(int codTarea) throws NotFoundException {
        throw new NotFoundException("La tarea con codigo xxxx no existe");
    }

    /**
     *  Devuelve el listado de todas las tareas.
     */
    public ArrayList<Tarea> findAll() {
        return null;
    }

    /**
     *  Devuelve el listado de todas las tareas cuyo atributo nombre coincide con @user
     */
    public ArrayList<Tarea> findAll(String user) {
        return null;
    }

}
