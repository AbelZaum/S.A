package com.senai.S.A.controller;

import com.senai.S.A.model.Colaborador;
import com.senai.S.A.service.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/colaboradores")
public class ColaboradorController {

    @Autowired
    private ColaboradorService colaboradorService;

    @GetMapping
    public String listarColaboradores(Model model) {
        model.addAttribute("colaboradores", colaboradorService.listarColaboradores());
        return "listacolaboradores";
    }

    @GetMapping("/novo")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("colaborador", new Colaborador());
        return "cadastrocolaborador";
    }

    @PostMapping
    public String salvarColaborador(@ModelAttribute Colaborador colaborador) {
        colaboradorService.salvarColaborador(colaborador);
        return "redirect:/colaboradores";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("colaborador", colaboradorService.buscarPorId(id));
        return "atualizarcolaborador";
    }

    @PostMapping("/editar/{id}")
    public String atualizarColaborador(@PathVariable Long id, @ModelAttribute Colaborador colaborador) {
        colaboradorService.atualizarColaborador(id, colaborador);
        return "redirect:/colaboradores";
    }

    @GetMapping("/excluir/{id}")
    public String excluirColaborador(@PathVariable Long id) {
        colaboradorService.excluirColaborador(id);
        return "redirect:/colaboradores";
    }
}
