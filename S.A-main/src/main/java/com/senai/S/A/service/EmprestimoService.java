package com.senai.S.A.service;

import com.senai.S.A.model.Emprestimo;
import com.senai.S.A.repository.EmprestimoRepository;
import com.senai.S.A.repository.TipoEquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private TipoEquipamentoRepository tipoEquipamentoRepository; // Injetar o EquipamentoRepository

    // Método para listar todos os empréstimos
    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }

    // Método para salvar um novo empréstimo
    @Transactional
    public Emprestimo salvarEmprestimo(Emprestimo emprestimo) {
        if (emprestimo.getColaborador() == null || emprestimo.getTipoEquipamento() == null) {
            throw new RuntimeException("Colaborador e Tipo de Equipamento são obrigatórios.");
        }

        emprestimo.setData(LocalDate.now());
        return emprestimoRepository.save(emprestimo);
    }

    // Método para buscar um empréstimo por ID
    public Emprestimo buscarPorId(Long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
    }

    // Método para registrar a devolução de um empréstimo
    @Transactional
    public void registrarDevolucao(Long id) {
        Emprestimo emprestimo = buscarPorId(id);
        emprestimo.setDevolucao(java.time.LocalDate.now());
        emprestimoRepository.save(emprestimo);
    }

    // Método para excluir um empréstimo
    @Transactional
    public void excluirEmprestimo(Long id) {
        emprestimoRepository.deleteById(id);
    }

    // Métodos para o Painel de Relatórios
    // Total de empréstimos realizados
    public int obterTotalEmprestimos() {
        return (int) emprestimoRepository.count();
    }

    // Percentual de devoluções no prazo
    public double obterPercentualDevolucoes() {
        long totalDevolvidos = emprestimoRepository.findAll()
                .stream()
                .filter(e -> e.getDevolucao() != null)
                .count();
        long totalEmprestimos = emprestimoRepository.count();

        return totalEmprestimos == 0 ? 0 : (double) totalDevolvidos / totalEmprestimos * 100;
    }

    // Empréstimos por Tipo de Equipamento
    public List<Map<String, Object>> obterEmprestimosPorTipo() {
        return emprestimoRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        e -> e.getTipoEquipamento().getDescricao(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("tipo", entry.getKey());
                    result.put("quantidade", entry.getValue().intValue()); // Convertendo Long para Integer
                    return result;
                })
                .collect(Collectors.toList());
    }

    // Status dos Empréstimos: Devolvidos x Não Devolvidos
    public List<Integer> obterStatusEmprestimos() {
        long totalDevolvidos = emprestimoRepository.findAll()
                .stream()
                .filter(e -> e.getDevolucao() != null)
                .count();
        long totalNaoDevolvidos = emprestimoRepository.count() - totalDevolvidos;

        return Arrays.asList((int) totalDevolvidos, (int) totalNaoDevolvidos);
    }

    // Empréstimos dentro e fora do prazo
    public List<Integer> obterPrazoEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();

        long noPrazo = emprestimos.stream()
                .filter(e -> e.getDevolucao() != null && !e.isForaDoPrazo())
                .count();

        long foraPrazo = emprestimos.stream()
                .filter(Emprestimo::isForaDoPrazo)
                .count();

        return Arrays.asList((int) noPrazo, (int) foraPrazo);
    }

    public double obterPercentualAtrasos() {
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();

        long totalForaPrazo = emprestimos.stream().filter(Emprestimo::isForaDoPrazo).count();
        long totalComDevolucao = emprestimos.stream().filter(e -> e.getDevolucao() != null).count();

        return totalComDevolucao == 0 ? 0 : (double) totalForaPrazo / totalComDevolucao * 100;
    }

}
