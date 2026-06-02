package es.progcipfpbatoi.todolistspring.model.repositories;

import es.progcipfpbatoi.todolistspring.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.todolistspring.exceptions.NotFoundException;
import es.progcipfpbatoi.todolistspring.model.daos.TareaDAO;
import es.progcipfpbatoi.todolistspring.model.entities.Tarea;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class TareaRepository {

    private TareaDAO tareaDAO;

    public TareaRepository(TareaDAO tareaDAO) {
        this.tareaDAO = tareaDAO;
    }

    public void add(Tarea tarea) throws DatabaseErrorException {
        try {
            tareaDAO.add(tarea);
        } catch (SQLException e) {
            throw new DatabaseErrorException("No s'ha pogut guardar la tasca en la base de dades");
        }
    }

    public Tarea get(int codTarea) throws NotFoundException, DatabaseErrorException {
        try {
            return tareaDAO.get(codTarea);
        } catch (SQLException e) {
            throw new DatabaseErrorException("No s'ha pogut consultar la tasca en la base de dades");
        }
    }

    public ArrayList<Tarea> findAll() throws DatabaseErrorException {
        try {
            return tareaDAO.findAll();
        } catch (SQLException e) {
            throw new DatabaseErrorException("No s'ha pogut consultar el llistat de tasques");
        }
    }

    public ArrayList<Tarea> findAll(String usuario) throws DatabaseErrorException {
        return findAll(usuario, null, null);
    }

    public ArrayList<Tarea> findAll(String usuario, LocalDate fecha, Boolean realizada)
            throws DatabaseErrorException {
        try {
            return tareaDAO.findAll(usuario, fecha, realizada);
        } catch (SQLException e) {
            throw new DatabaseErrorException("No s'ha pogut fer la cerca de tasques");
        }
    }

    public Tarea delete(int codTarea) throws NotFoundException, DatabaseErrorException {
        try {
            return tareaDAO.delete(codTarea);
        } catch (SQLException e) {
            throw new DatabaseErrorException("No s'ha pogut eliminar la tasca de la base de dades");
        }
    }
}
