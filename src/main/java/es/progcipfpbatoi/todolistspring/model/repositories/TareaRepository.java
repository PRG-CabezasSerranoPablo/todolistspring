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

    // DAO que se encarga de guardar y leer los datos en la tecnologia de esta rama.
    private TareaDAO tareaDAO;

    // Spring inyecta aqui el DAO que corresponda, en esta rama el de Perst.
    public TareaRepository(TareaDAO tareaDAO) {
        this.tareaDAO = tareaDAO;
    }

    // Guarda una tarea nueva. Si falla la base de datos, lanzo una excepcion mas clara.
    public void add(Tarea tarea) throws DatabaseErrorException {
        try {
            tareaDAO.add(tarea);
        } catch (SQLException e) {
            throw new DatabaseErrorException("No s'ha pogut guardar la tasca en la base de dades");
        }
    }

    // Devuelve una tarea concreta por codigo.
    public Tarea get(int codTarea) throws NotFoundException, DatabaseErrorException {
        try {
            return tareaDAO.get(codTarea);
        } catch (SQLException e) {
            throw new DatabaseErrorException("No s'ha pogut consultar la tasca en la base de dades");
        }
    }

    // Devuelve todas las tareas guardadas.
    public ArrayList<Tarea> findAll() throws DatabaseErrorException {
        try {
            return tareaDAO.findAll();
        } catch (SQLException e) {
            throw new DatabaseErrorException("No s'ha pogut consultar el llistat de tasques");
        }
    }

    // Busca por usuario usando el metodo general de busqueda.
    public ArrayList<Tarea> findAll(String usuario) throws DatabaseErrorException {
        return findAll(usuario, null, null);
    }

    // Busca tareas aplicando los filtros que llegan desde el formulario.
    public ArrayList<Tarea> findAll(String usuario, LocalDate fecha, Boolean realizada)
            throws DatabaseErrorException {
        try {
            return tareaDAO.findAll(usuario, fecha, realizada);
        } catch (SQLException e) {
            throw new DatabaseErrorException("No s'ha pogut fer la cerca de tasques");
        }
    }

    // Borra una tarea y devuelve la tarea borrada para poder mostrar informacion al usuario.
    public Tarea delete(int codTarea) throws NotFoundException, DatabaseErrorException {
        try {
            return tareaDAO.delete(codTarea);
        } catch (SQLException e) {
            throw new DatabaseErrorException("No s'ha pogut eliminar la tasca de la base de dades");
        }
    }
}
