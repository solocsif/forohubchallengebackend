package com.javier.forohubchallengebackend.controller;

import com.javier.forohubchallengebackend.entities.Topico;
import com.javier.forohubchallengebackend.dto.RegistroTopicoDTO;
import com.javier.forohubchallengebackend.dto.TopicoResponse;
import com.javier.forohubchallengebackend.servicio.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService servicio;

    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("registroTopicoDTO", new RegistroTopicoDTO());
        return "registro"; // sin topicos en el modelo
    }

    @PostMapping
    public String registrarTopico(
            @Valid @ModelAttribute("registroTopicoDTO") RegistroTopicoDTO dto,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            List<TopicoResponse> topicos = servicio.listar(); // ✅ corregido
            model.addAttribute("topicos", topicos);
            return "registro";
        }

        servicio.registrarTopico(dto.toRequest());
        return "redirect:/topicos/formulario";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        servicio.eliminarTopico(id);
        return "redirect:/topicos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        RegistroTopicoDTO dto = servicio.obtenerDTOParaEdicion(id);
        model.addAttribute("registroTopicoDTO", dto);
        model.addAttribute("topicoId", id); // ✅ para saber si estamos editando
        model.addAttribute("topicos", servicio.listar()); // ✅ mostrar tabla
        return "registro"; // ✅ reutiliza la misma vista
    }

    @PostMapping("/editar/{id}")
    public String actualizarTopico(@PathVariable Long id, @Valid @ModelAttribute("registroTopicoDTO") RegistroTopicoDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return "editar";
        }
        servicio.actualizarTopico(id, dto.toRequest());
        return "redirect:/topicos/formulario";
    }

}