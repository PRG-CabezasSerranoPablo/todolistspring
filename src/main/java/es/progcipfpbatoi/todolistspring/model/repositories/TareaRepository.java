package es.progcipfpbatoi.todolistspring.model.repositories;

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

    public void add(Tarea tarea) {
        try {
            tareaDAO.add(tarea);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Tarea get(int codTarea) throws NotFoundException {
        try {
            return tareaDAO.get(codTarea);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Tarea> findAll() {
        try {
            return tareaDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Tarea> findAll(String usuario) {
        return findAll(usuario, null, null);
    }

    public ArrayList<Tarea> findAll(String usuario, LocalDate fecha, Boolean realizada) {
        try {
            return tareaDAO.findAll(usuario, fecha, realizada);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Tarea delete(int codTarea) throws NotFoundException {
        try {
            return tareaDAO.delete(codTarea);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
