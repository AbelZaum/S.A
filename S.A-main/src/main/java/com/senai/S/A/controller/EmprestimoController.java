package com.senai.S.A.controller;

import com.senai.S.A.model.Colaborador;
import com.senai.S.A.model.Emprestimo;
import com.senai.S.A.service.ColaboradorService;
import com.senai.S.A.service.EmprestimoService;
import com.senai.S.A.service.TipoEquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @Autowired
    private ColaboradorService colaboradorService;

    @Autowired
    private TipoEquipamentoService tipoEquipamentoService;

    // Listar todos os empréstimos
    @GetMapping
    public String listarEmprestimos(Model model) {
        List<Emprestimo> emprestimos = emprestimoService.listarEmprestimos();
        model.addAttribute("emprestimos", emprestimos);
        return "listaemprestimos";
    }

    // Mostrar formulário de registro de empréstimo
    @GetMapping("/novo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("emprestimo", new Emprestimo());
        model.addAttribute("colaboradores", colaboradorService.listarColaboradores());
        model.addAttribute("tipos", tipoEquipamentoService.listarTiposDeEquipamentos()); // Carregar tipos de equipamento
        return "cadastroemprestimo";
    }

    // Registrar novo empréstimo
    @PostMapping
    public String registrarEmprestimo(@ModelAttribute Emprestimo emprestimo) {
        System.out.println("Recebendo empréstimo:");
        System.out.println("Colaborador: " + emprestimo.getColaborador());
        System.out.println("Tipo de Equipamento: " + emprestimo.getTipoEquipamento());

        emprestimoService.salvarEmprestimo(emprestimo);
        return "redirect:/emprestimos";
    }

    // Mostrar formulário de devolução
    @GetMapping("/devolucao/{id}")
    public String mostrarFormularioDevolucao(@PathVariable Long id, Model model) {
        Emprestimo emprestimo = emprestimoService.buscarPorId(id);
        model.addAttribute("emprestimo", emprestimo); // Certifique-se de que o objeto está sendo passado corretamente
        return "devolucaoemprestimo";
    }

    // Registrar devolução de empréstimo
    @PostMapping("/devolucao/{id}")
    public String registrarDevolucao(@PathVariable Long id) {
        emprestimoService.registrarDevolucao(id);
        return "redirect:/emprestimos";
    }

    @GetMapping("/historico/{colaboradorId}")
    public String historicoPorColaborador(@PathVariable Long colaboradorId, Model model) {
        List<Emprestimo> historico = emprestimoService.listarEmprestimosPorColaborador(colaboradorId);
        Colaborador colaborador = colaboradorService.buscarPorId(colaboradorId);
        model.addAttribute("historico", historico);
        model.addAttribute("colaborador", colaborador);
        return "historicoemprestimos";
    }

    // Excluir empréstimo
    @GetMapping("/excluir/{id}")
    public String excluirEmprestimo(@PathVariable Long id) {
        emprestimoService.excluirEmprestimo(id);
        return "redirect:/emprestimos";
    }

    // Endpoint para listar equipamentos por tipo via AJAX
    //@GetMapping("/tipo/{tipoId}")
    //@ResponseBody
    //public List<Equipamento> listarEquipamentosPorTipo(@PathVariable Long tipoId) {
    //return tipoEquipamentoService.listarPorTipo(tipoId);
    //}
}
