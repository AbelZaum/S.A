package com.senai.S.A.controller;

import com.senai.S.A.model.TipoEquipamento;
import com.senai.S.A.service.TipoEquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tipos-equipamentos")
public class TipoEquipamentoController {

    @Autowired
    private TipoEquipamentoService tipoEquipamentoService;

    @GetMapping
    public String listarTipos(Model model) {
        model.addAttribute("tipos", tipoEquipamentoService.listarTipos());
        return "listatipoequipamentos";
    }

    @GetMapping("/novo")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("tipoEquipamento", new TipoEquipamento());
        return "cadastrotipoequipamento";
    }

    @PostMapping
    public String salvarTipo(@ModelAttribute TipoEquipamento tipo) {
        tipoEquipamentoService.salvarTipo(tipo);
        return "redirect:/tipos-equipamentos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        TipoEquipamento tipoEquipamento = tipoEquipamentoService.buscarPorId(id);
        model.addAttribute("tipoEquipamento", tipoEquipamento);
        model.addAttribute("tipos", tipoEquipamentoService.listarTipos()); // Lista de tipos para o dropdown
        return "atualizartipoequipamento";
    }

    @PostMapping("/editar/{id}")
    public String atualizarTipo(@PathVariable Long id, @ModelAttribute TipoEquipamento tipo) {
        if (tipo.getTipo() == null || tipo.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'tipo' não pode ser vazio.");
        }
        tipoEquipamentoService.atualizarTipo(id, tipo);
        return "redirect:/tipos-equipamentos";
    }

    @GetMapping("/excluir/{id}")
    public String excluirTipo(@PathVariable Long id, Model model) {
        try {
            tipoEquipamentoService.excluirTipo(id);
            return "redirect:/tipos-equipamentos";
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("tipos", tipoEquipamentoService.listarTipos());
            return "listatipoequipamentos"; // Volta para a lista com a mensagem de erro
        }
    }

}
