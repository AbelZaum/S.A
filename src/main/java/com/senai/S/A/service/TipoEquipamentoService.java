package com.senai.S.A.service;

import com.senai.S.A.model.TipoEquipamento;
import com.senai.S.A.repository.TipoEquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoEquipamentoService {

    @Autowired
    private TipoEquipamentoRepository tipoEquipamentoRepository;

    public List<TipoEquipamento> listarTipos() {
        return tipoEquipamentoRepository.findAll();
    }

    public TipoEquipamento buscarPorId(Long id) {
        return tipoEquipamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo de equipamento não encontrado"));
    }

    @Transactional
    public TipoEquipamento salvarTipo(TipoEquipamento tipo) {
        return tipoEquipamentoRepository.save(tipo);
    }

    @Transactional
    public TipoEquipamento atualizarTipo(Long id, TipoEquipamento tipoAtualizado) {
        TipoEquipamento tipo = buscarPorId(id);
        tipo.setDescricao(tipoAtualizado.getDescricao());
        return tipoEquipamentoRepository.save(tipo);
    }

    @Transactional
    public void excluirTipo(Long id) {
        tipoEquipamentoRepository.deleteById(id);
    }

    // Método para listar todos os tipos de equipamentos
    public List<TipoEquipamento> listarTiposDeEquipamentos() {
        return tipoEquipamentoRepository.findAll();
    }
}
