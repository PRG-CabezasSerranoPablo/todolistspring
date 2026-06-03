package es.progcipfpbatoi.todolistspring.model.daos;

import es.progcipfpbatoi.todolistspring.model.entities.Tarea;
import org.garret.perst.Persistent;

import java.util.ArrayList;

// Objeto raiz de Perst. Desde aqui se accede a las tareas guardadas.
public class PerstRoot extends Persistent {

    // Lista de tareas que se queda guardada dentro del fichero de Perst.
    private ArrayList<Tarea> tareas = new ArrayList<>();
    // Codigo que se usara para la proxima tarea nueva.
    private int siguienteCodigo = 1;

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public int getSiguienteCodigo() {
        return siguienteCodigo;
    }

    public void setSiguienteCodigo(int siguienteCodigo) {
        this.siguienteCodigo = siguienteCodigo;
    }
}
