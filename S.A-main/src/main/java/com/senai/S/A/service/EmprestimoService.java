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

        if (emprestimo.getPrazo() <= 0) {
            throw new RuntimeException("O prazo deve ser maior que 0.");
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

    public List<Emprestimo> listarEmprestimosPorColaborador(Long colaboradorId) {
        return emprestimoRepository.findAll().stream()
                .filter(e -> e.getColaborador().getId().equals(colaboradorId))
                .collect(Collectors.toList());
    }

}
