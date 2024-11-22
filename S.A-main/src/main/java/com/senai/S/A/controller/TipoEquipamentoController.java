package com.senai.S.A.controller;

import com.senai.S.A.model.TipoEquipamento;
import com.senai.S.A.service.TipoEquipamentoService;
import java.util.List;
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
        model.addAttribute("tipoEquipamento", tipoEquipamentoService.buscarPorId(id));
        List<TipoEquipamento> tipos = tipoEquipamentoService.listarTipos(); // Método que retorna a lista de tipos
        model.addAttribute("tipos", tipos);
        return "atualizartipoequipamento";
    }

    @PostMapping("/editar/{id}")
    public String atualizarTipo(@PathVariable Long id, @ModelAttribute TipoEquipamento tipo) {
        tipoEquipamentoService.atualizarTipo(id, tipo);

        return "redirect:/tipos-equipamentos";
    }

    @GetMapping("/excluir/{id}")
    public String excluirTipo(@PathVariable Long id) {
        tipoEquipamentoService.excluirTipo(id);
        return "redirect:/tipos-equipamentos";
    }
}
