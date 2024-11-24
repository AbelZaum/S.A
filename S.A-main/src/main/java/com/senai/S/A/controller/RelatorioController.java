package com.senai.S.A.controller;

import com.senai.S.A.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping
    public String exibirPainel(Model model) {
        model.addAttribute("totalEmprestimos", emprestimoService.obterTotalEmprestimos());
        model.addAttribute("percentualDevolucoes", emprestimoService.obterPercentualDevolucoes());
        model.addAttribute("percentualAtrasos", emprestimoService.obterPercentualAtrasos());
        model.addAttribute("prazoEmprestimos", emprestimoService.obterPrazoEmprestimos());
        model.addAttribute("emprestimosPorTipo", emprestimoService.obterEmprestimosPorTipo());
        model.addAttribute("statusEmprestimos", emprestimoService.obterStatusEmprestimos());
        return "dashboard";
    }

}
