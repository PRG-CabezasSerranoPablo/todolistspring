package es.progcipfpbatoi.todolistspring.controllers;

import es.progcipfpbatoi.todolistspring.model.entities.Tarea;
import es.progcipfpbatoi.todolistspring.model.repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class TareaController {

    @Autowired
    private TareaRepository tareaRepository;

    @GetMapping("/tarea-form")
    public String tareaFormActionView(){
        return "tarea_form_view";
    }

    @PostMapping(value = "/tarea-add")
    @ResponseBody
    public String postAddAction(@RequestParam Map<String, String> params) {
        int code = Integer.parseInt(params.get("code"));
        String user = params.get("user");
        String descripcion = params.get("description");
        Tarea tarea = new Tarea(code, user, descripcion);
        tareaRepository.add(tarea);
        return "<html>" +
                "<body>Tarea " + tarea.getCodigo() + " recibida con éxito</body>" +
                "</html>";
    }

}
