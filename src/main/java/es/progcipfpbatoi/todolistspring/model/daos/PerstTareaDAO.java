package es.progcipfpbatoi.todolistspring.model.daos;

import es.progcipfpbatoi.todolistspring.exceptions.NotFoundException;
import es.progcipfpbatoi.todolistspring.model.entities.Tarea;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class PerstTareaDAO implements TareaDAO {

    // Nombre del fichero donde Perst guarda la informacion.
    @Value("${app.perst.file}")
    private String perstFile;

    private Storage storage;
    private PerstRoot root;

    // Al arrancar la aplicacion, abro el fichero de Perst y preparo el objeto raiz.
    @PostConstruct
    public void init() {
        storage = StorageFactory.getInstance().createStorage();
        storage.open(perstFile);

        // Si la base esta vacia, creo la raiz por primera vez.
        if (storage.getRoot() == null) {
            root = new PerstRoot();
            storage.setRoot(root);
            storage.commit();
        } else {
            root = (PerstRoot) storage.getRoot();
        }
    }

    // Cuando se cierra la aplicacion, cierro tambien la base Perst.
    @PreDestroy
    public void close() {
        if (storage != null) {
            storage.close();
        }
    }

    // Anade una tarea y le pone un codigo nuevo automaticamente.
    @Override
    public void add(Tarea tarea) throws SQLException {
        tarea.setCodigo(root.getSiguienteCodigo());
        root.setSiguienteCodigo(root.getSiguienteCodigo() + 1);
        root.getTareas().add(tarea);
        guardarCambios();
    }

    // Busca una tarea por codigo recorriendo la lista guardada en Perst.
    @Override
    public Tarea get(int codTarea) throws SQLException, NotFoundException {
        for (Tarea tarea : root.getTareas()) {
            if (tarea.getCodigo() == codTarea) {
                return tarea;
            }
        }

        throw new NotFoundException("La tasca amb codi " + codTarea + " no existeix");
    }

    // Devuelve una copia de la lista de tareas para no trabajar directamente sobre la original.
    @Override
    public ArrayList<Tarea> findAll() throws SQLException {
        return new ArrayList<>(root.getTareas());
    }

    // Filtra las tareas por usuario, fecha y estado si esos filtros vienen rellenos.
    @Override
    public ArrayList<Tarea> findAll(String usuario, LocalDate fecha, Boolean realizada) throws SQLException {
        ArrayList<Tarea> tareasEncontradas = new ArrayList<>();

        for (Tarea tarea : root.getTareas()) {
            // Cada filtro se aplica solo si el usuario lo ha puesto en el formulario.
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

    // Borra una tarea si existe. Si no existe, get lanza NotFoundException.
    @Override
    public Tarea delete(int codTarea) throws SQLException, NotFoundException {
        Tarea tarea = get(codTarea);
        root.getTareas().remove(tarea);
        guardarCambios();
        return tarea;
    }

    private void guardarCambios() {
        // Guardo la raiz para conservar la lista y el siguiente codigo.
        storage.store(root);
        storage.commit();
    }
}
