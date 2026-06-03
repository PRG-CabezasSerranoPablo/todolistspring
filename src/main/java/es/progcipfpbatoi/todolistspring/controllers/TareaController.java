package es.progcipfpbatoi.todolistspring.controllers;

import es.progcipfpbatoi.todolistspring.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.todolistspring.exceptions.NotFoundException;
import es.progcipfpbatoi.todolistspring.model.entities.Prioridad;
import es.progcipfpbatoi.todolistspring.model.entities.Tarea;
import es.progcipfpbatoi.todolistspring.model.repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@Controller
public class TareaController {

    // Repositorio que uso para trabajar con las tareas sin acceder directamente a la base de datos.
    @Autowired
    private TareaRepository tareaRepository;

    // Muestra la pantalla principal con los enlaces a las opciones de la aplicacion.
    @GetMapping("/")
    public String menuActionView() {
        return "menu_view";
    }

    // Muestra el formulario para crear una tarea nueva.
    @GetMapping("/tarea-form")
    public String tareaFormActionView() {
        return "tarea_form_view";
    }

    // Busca todas las tareas y se las pasa a la vista para pintarlas en una tabla.
    @GetMapping("/tareas-list")
    public String tareasListActionView(Model model) {
        try {
            model.addAttribute("tareas", tareaRepository.findAll());
            return "tarea_list_view";
        } catch (DatabaseErrorException e) {
            return databaseError(model, e);
        }
    }

    // Abre el formulario de busqueda sin resultados todavia.
    @GetMapping("/tareas-buscar")
    public String tareasBuscarActionView() {
        return "tarea_search_view";
    }

    // Recoge los filtros del formulario de busqueda y muestra las tareas encontradas.
    @GetMapping("/tareas-search")
    public String tareasSearchActionView(@RequestParam Map<String, String> params, Model model) {
        String usuario = params.get("usuario");
        LocalDate fecha = null;
        Boolean realizada = null;

        // Si el usuario no ha puesto fecha, se deja a null para no filtrar por fecha.
        if (params.get("fecha") != null && !params.get("fecha").isBlank()) {
            fecha = LocalDate.parse(params.get("fecha"));
        }

        // Si no se elige estado, se buscan tanto tareas hechas como no hechas.
        if (params.get("realizada") != null && !params.get("realizada").isBlank()) {
            realizada = Boolean.valueOf(params.get("realizada"));
        }

        try {
            model.addAttribute("tareas", tareaRepository.findAll(usuario, fecha, realizada));
            return "tarea_search_view";
        } catch (DatabaseErrorException e) {
            return databaseError(model, e);
        }
    }

    // Muestra todos los datos de una tarea concreta.
    @GetMapping("/tarea-detail")
    public String tareaDetailActionView(@RequestParam int codigo, Model model) {
        try {
            model.addAttribute("tarea", tareaRepository.get(codigo));
            return "tarea_detail_view";
        } catch (NotFoundException e) {
            model.addAttribute("titulo", "Tasca no trobada");
            model.addAttribute("mensaje", e.getMessage());
            return "message_view";
        } catch (DatabaseErrorException e) {
            return databaseError(model, e);
        }
    }

    // Elimina una tarea usando su codigo y luego muestra un mensaje al usuario.
    @GetMapping("/tarea-delete")
    public String tareaDeleteActionView(@RequestParam int codigo, Model model) {
        try {
            Tarea tarea = tareaRepository.delete(codigo);
            model.addAttribute("titulo", "Tasca eliminada");
            model.addAttribute("mensaje", "Tasca " + tarea.getCodigo() + " eliminada amb exit");
        } catch (NotFoundException e) {
            model.addAttribute("titulo", "Tasca no trobada");
            model.addAttribute("mensaje", e.getMessage());
        } catch (DatabaseErrorException e) {
            return databaseError(model, e);
        }

        return "message_view";
    }

    // Procesa el formulario de alta y guarda la tarea nueva.
    @PostMapping("/tarea-add")
    public String postAddAction(@RequestParam Map<String, String> params, Model model) {
        String descripcion = params.get("descripcion");
        String usuario = params.get("usuario");
        String categoria = params.get("categoria");
        Prioridad prioridad = Prioridad.valueOf(params.get("prioridad"));
        boolean realizada = params.containsKey("realizada");

        // El formulario pide fecha y hora por separado, pero la tarea guarda un solo dato.
        LocalDate fecha = LocalDate.parse(params.get("fecha"));
        LocalTime hora = LocalTime.parse(params.get("hora"));
        LocalDateTime fechaVencimiento = LocalDateTime.of(fecha, hora);

        Tarea tarea = new Tarea(0, descripcion, usuario, fechaVencimiento, prioridad, categoria, realizada);
        try {
            tareaRepository.add(tarea);
        } catch (DatabaseErrorException e) {
            return databaseError(model, e);
        }

        return "redirect:/tareas-buscar";
    }

    // Metodo comun para mostrar errores relacionados con la base de datos.
    private String databaseError(Model model, DatabaseErrorException e) {
        model.addAttribute("titulo", "Error de base de dades");
        model.addAttribute("mensaje", e.getMessage());
        return "message_view";
    }
}
