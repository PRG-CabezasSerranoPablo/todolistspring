package es.progcipfpbatoi.todolistspring.model.daos;

import es.progcipfpbatoi.todolistspring.exceptions.NotFoundException;
import es.progcipfpbatoi.todolistspring.model.entities.Tarea;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

// Esta interfaz dice que operaciones debe tener cualquier forma de guardar tareas.
public interface TareaDAO {

    // Guarda una tarea nueva.
    void add(Tarea tarea) throws SQLException;

    // Obtiene una tarea concreta por su codigo.
    Tarea get(int codTarea) throws SQLException, NotFoundException;

    // Devuelve todas las tareas.
    ArrayList<Tarea> findAll() throws SQLException;

    // Devuelve tareas filtrando por usuario, fecha o si esta realizada.
    ArrayList<Tarea> findAll(String usuario, LocalDate fecha, Boolean realizada) throws SQLException;

    // Elimina una tarea por su codigo.
    Tarea delete(int codTarea) throws SQLException, NotFoundException;
}
