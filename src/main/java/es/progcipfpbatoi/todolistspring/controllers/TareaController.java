package es.progcipfpbatoi.todolistspring.controllers;

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

    // Repositorio donde se guardan las tareas de esta rama en memoria.
    @Autowired
    private TareaRepository tareaRepository;

    // Muestra la pantalla principal del proyecto.
    @GetMapping("/")
    public String menuActionView() {
        return "menu_view";
    }

    // Muestra el formulario para crear una tarea nueva.
    @GetMapping("/tarea-form")
    public String tareaFormActionView() {
        return "tarea_form_view";
    }

    // Muestra el listado completo de tareas.
    @GetMapping("/tareas-list")
    public String tareasListActionView(Model model) {
        model.addAttribute("tareas", tareaRepository.findAll());
        return "tarea_list_view";
    }

    // Muestra la pantalla de busqueda.
    @GetMapping("/tareas-buscar")
    public String tareasBuscarActionView() {
        return "tarea_search_view";
    }

    // Recoge los filtros del formulario y busca las tareas que coinciden.
    @GetMapping("/tareas-search")
    public String tareasSearchActionView(@RequestParam Map<String, String> params, Model model) {
        String usuario = params.get("usuario");
        LocalDate fecha = null;
        Boolean realizada = null;

        if (params.get("fecha") != null && !params.get("fecha").isBlank()) {
            fecha = LocalDate.parse(params.get("fecha"));
        }

        if (params.get("realizada") != null && !params.get("realizada").isBlank()) {
            realizada = Boolean.valueOf(params.get("realizada"));
        }

        model.addAttribute("tareas", tareaRepository.findAll(usuario, fecha, realizada));
        return "tarea_search_view";
    }

    // Muestra los datos de una tarea concreta.
    @GetMapping("/tarea-detail")
    public String tareaDetailActionView(@RequestParam("codigo") int codigo, Model model) {
        try {
            model.addAttribute("tarea", tareaRepository.get(codigo));
            return "tarea_detail_view";
        } catch (NotFoundException e) {
            model.addAttribute("titulo", "Tasca no trobada");
            model.addAttribute("mensaje", e.getMessage());
            return "message_view";
        }
    }

    // Borra una tarea y muestra un mensaje con el resultado.
    @GetMapping("/tarea-delete")
    public String tareaDeleteActionView(@RequestParam("codigo") int codigo, Model model) {
        try {
            Tarea tarea = tareaRepository.delete(codigo);
            model.addAttribute("titulo", "Tasca eliminada");
            model.addAttribute("mensaje", "Tasca " + tarea.getCodigo() + " eliminada amb exit");
        } catch (NotFoundException e) {
            model.addAttribute("titulo", "Tasca no trobada");
            model.addAttribute("mensaje", e.getMessage());
        }

        return "message_view";
    }

    // Recibe los datos del formulario, crea el objeto Tarea y lo guarda.
    @PostMapping("/tarea-add")
    public String postAddAction(@RequestParam Map<String, String> params) {
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
        tareaRepository.add(tarea);

        return "redirect:/tareas-buscar";
    }
}
