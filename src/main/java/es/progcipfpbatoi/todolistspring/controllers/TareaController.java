package es.progcipfpbatoi.todolistspring.controllers;

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

    @Autowired
    private TareaRepository tareaRepository;

    @GetMapping("/")
    public String menuActionView() {
        return "menu_view";
    }

    @GetMapping("/tarea-form")
    public String tareaFormActionView() {
        return "tarea_form_view";
    }

    @GetMapping("/tareas-list")
    public String tareasListActionView(Model model) {
        model.addAttribute("tareas", tareaRepository.findAll());
        return "tarea_list_view";
    }

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
