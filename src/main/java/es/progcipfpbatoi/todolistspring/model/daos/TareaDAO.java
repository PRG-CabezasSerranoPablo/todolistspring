package es.progcipfpbatoi.todolistspring.model.daos;

import es.progcipfpbatoi.todolistspring.exceptions.NotFoundException;
import es.progcipfpbatoi.todolistspring.model.entities.Tarea;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

// Interfaz comun para que el repositorio no dependa de una base de datos concreta.
public interface TareaDAO {

    // Guarda una tarea.
    void add(Tarea tarea) throws SQLException;

    // Busca una tarea por codigo.
    Tarea get(int codTarea) throws SQLException, NotFoundException;

    // Devuelve todas las tareas.
    ArrayList<Tarea> findAll() throws SQLException;

    // Devuelve las tareas que coinciden con los filtros.
    ArrayList<Tarea> findAll(String usuario, LocalDate fecha, Boolean realizada) throws SQLException;

    // Elimina una tarea por codigo.
    Tarea delete(int codTarea) throws SQLException, NotFoundException;
}
