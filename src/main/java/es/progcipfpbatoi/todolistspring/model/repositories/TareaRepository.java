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

    // El repositorio usa un DAO para no depender directamente de como se guardan las tareas.
    private TareaDAO tareaDAO;

    // Spring mete aqui el DAO que corresponde a esta rama del proyecto.
    public TareaRepository(TareaDAO tareaDAO) {
        this.tareaDAO = tareaDAO;
    }

    // Guarda una tarea y convierte errores SQL en un error propio de la aplicacion.
    public void add(Tarea tarea) throws DatabaseErrorException {
        try {
            tareaDAO.add(tarea);
        } catch (SQLException e) {
            throw new DatabaseErrorException("No s'ha pogut guardar la tasca en la base de dades");
        }
    }

    // Busca una tarea por su codigo.
    public Tarea get(int codTarea) throws NotFoundException, DatabaseErrorException {
        try {
            return tareaDAO.get(codTarea);
        } catch (SQLException e) {
            throw new DatabaseErrorException("No s'ha pogut consultar la tasca en la base de dades");
        }
    }

    // Devuelve todas las tareas que hay guardadas.
    public ArrayList<Tarea> findAll() throws DatabaseErrorException {
        try {
            return tareaDAO.findAll();
        } catch (SQLException e) {
            throw new DatabaseErrorException("No s'ha pogut consultar el llistat de tasques");
        }
    }

    // Busca por usuario y deja el resto de filtros vacios.
    public ArrayList<Tarea> findAll(String usuario) throws DatabaseErrorException {
        return findAll(usuario, null, null);
    }

    // Busca tareas aplicando los filtros que se hayan rellenado en el formulario.
    public ArrayList<Tarea> findAll(String usuario, LocalDate fecha, Boolean realizada)
            throws DatabaseErrorException {
        try {
            return tareaDAO.findAll(usuario, fecha, realizada);
        } catch (SQLException e) {
            throw new DatabaseErrorException("No s'ha pogut fer la cerca de tasques");
        }
    }

    // Borra una tarea y devuelve la tarea borrada para poder mostrar informacion si hiciera falta.
    public Tarea delete(int codTarea) throws NotFoundException, DatabaseErrorException {
        try {
            return tareaDAO.delete(codTarea);
        } catch (SQLException e) {
            throw new DatabaseErrorException("No s'ha pogut eliminar la tasca de la base de dades");
        }
    }
}
