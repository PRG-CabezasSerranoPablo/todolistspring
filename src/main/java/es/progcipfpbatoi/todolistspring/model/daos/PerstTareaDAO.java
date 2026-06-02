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

    @Value("${app.perst.file}")
    private String perstFile;

    private Storage storage;
    private PerstRoot root;

    @PostConstruct
    public void init() {
        storage = StorageFactory.getInstance().createStorage();
        storage.open(perstFile);

        if (storage.getRoot() == null) {
            root = new PerstRoot();
            storage.setRoot(root);
            storage.commit();
        } else {
            root = (PerstRoot) storage.getRoot();
        }
    }

    @PreDestroy
    public void close() {
        if (storage != null) {
            storage.close();
        }
    }

    @Override
    public void add(Tarea tarea) throws SQLException {
        tarea.setCodigo(root.getSiguienteCodigo());
        root.setSiguienteCodigo(root.getSiguienteCodigo() + 1);
        root.getTareas().add(tarea);
        guardarCambios();
    }

    @Override
    public Tarea get(int codTarea) throws SQLException, NotFoundException {
        for (Tarea tarea : root.getTareas()) {
            if (tarea.getCodigo() == codTarea) {
                return tarea;
            }
        }

        throw new NotFoundException("La tasca amb codi " + codTarea + " no existeix");
    }

    @Override
    public ArrayList<Tarea> findAll() throws SQLException {
        return new ArrayList<>(root.getTareas());
    }

    @Override
    public ArrayList<Tarea> findAll(String usuario, LocalDate fecha, Boolean realizada) throws SQLException {
        ArrayList<Tarea> tareasEncontradas = new ArrayList<>();

        for (Tarea tarea : root.getTareas()) {
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

    @Override
    public Tarea delete(int codTarea) throws SQLException, NotFoundException {
        Tarea tarea = get(codTarea);
        root.getTareas().remove(tarea);
        guardarCambios();
        return tarea;
    }

    private void guardarCambios() {
        // Guardem l'arrel per a conservar la llista i el seguent codi.
        storage.store(root);
        storage.commit();
    }
}
