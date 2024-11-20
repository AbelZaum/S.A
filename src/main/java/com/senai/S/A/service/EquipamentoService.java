package com.senai.S.A.service;

import com.senai.S.A.model.Equipamento;
import com.senai.S.A.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    // Listar todos os equipamentos
    public List<Equipamento> listarEquipamentos() {
        List<Equipamento> equipamentos = equipamentoRepository.findAll();
        if (equipamentos.isEmpty()) {
            System.out.println("Nenhum equipamento cadastrado!");
        }
        return equipamentos;
    }

    // Buscar equipamento por ID
    public Equipamento buscarPorId(Long id) {
        return equipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));
    }

    // Salvar novo equipamento
    @Transactional
    public Equipamento salvarEquipamento(Equipamento equipamento) {
        if (equipamento.getDescricao() == null || equipamento.getDescricao().isEmpty()) {
            throw new RuntimeException("Descrição do equipamento é obrigatória.");
        }
        if (equipamento.getTipo() == null) {
            throw new RuntimeException("Tipo do equipamento é obrigatório.");
        }
        return equipamentoRepository.save(equipamento);
    }

    // Atualizar equipamento existente
    @Transactional
    public Equipamento atualizarEquipamento(Long id, Equipamento equipamentoAtualizado) {
        Equipamento equipamento = buscarPorId(id);
        equipamento.setDescricao(equipamentoAtualizado.getDescricao());
        equipamento.setTipo(equipamentoAtualizado.getTipo());
        return equipamentoRepository.save(equipamento);
    }

    // Excluir equipamento por ID
    @Transactional
    public void excluirEquipamento(Long id) {
        equipamentoRepository.deleteById(id);
    }

    // Listar equipamentos por tipo
    public List<Equipamento> listarPorTipo(Long tipoId) {
        return equipamentoRepository.findByTipoId(tipoId);
    }
}
