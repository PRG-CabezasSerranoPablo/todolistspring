package es.progcipfpbatoi.todolistspring.model.daos;

import es.progcipfpbatoi.todolistspring.exceptions.NotFoundException;
import es.progcipfpbatoi.todolistspring.model.entities.Tarea;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface TareaDAO {

    void add(Tarea tarea) throws SQLException;

    Tarea get(int codTarea) throws SQLException, NotFoundException;

    ArrayList<Tarea> findAll() throws SQLException;

    ArrayList<Tarea> findAll(String usuario, LocalDate fecha, Boolean realizada) throws SQLException;

    Tarea delete(int codTarea) throws SQLException, NotFoundException;
}
