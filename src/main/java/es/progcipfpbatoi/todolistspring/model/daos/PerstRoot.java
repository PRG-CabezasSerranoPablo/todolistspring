package es.progcipfpbatoi.todolistspring.model.daos;

import es.progcipfpbatoi.todolistspring.model.entities.Tarea;
import org.garret.perst.Persistent;

import java.util.ArrayList;

public class PerstRoot extends Persistent {

    private ArrayList<Tarea> tareas = new ArrayList<>();
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
