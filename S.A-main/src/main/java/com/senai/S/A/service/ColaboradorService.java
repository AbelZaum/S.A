package com.senai.S.A.service;

import com.senai.S.A.model.Colaborador;
import com.senai.S.A.repository.ColaboradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public List<Colaborador> listarColaboradores() {
        return colaboradorRepository.findAll();
    }

    public Colaborador buscarPorId(Long id) {
        return colaboradorRepository.findById(id).orElseThrow(() -> new RuntimeException("Colaborador não encontrado"));
    }

    @Transactional
    public Colaborador salvarColaborador(Colaborador colaborador) {
        if (Period.between(colaborador.getNascimento(), LocalDate.now()).getYears() >= 100) {
            throw new RuntimeException("Idade não pode ser maior ou igual a 100 anos");
        }
        return colaboradorRepository.save(colaborador);
    }

    @Transactional
    public Colaborador atualizarColaborador(Long id, Colaborador colaboradorAtualizado) {
        Colaborador colaborador = buscarPorId(id);
        colaborador.setNome(colaboradorAtualizado.getNome());
        colaborador.setEmail(colaboradorAtualizado.getEmail());
        colaborador.setFuncao(colaboradorAtualizado.getFuncao());
        colaborador.setNascimento(colaboradorAtualizado.getNascimento());
        return colaboradorRepository.save(colaborador);
    }

    @Transactional
    public void excluirColaborador(Long id) {
        colaboradorRepository.deleteById(id);
    }
}
