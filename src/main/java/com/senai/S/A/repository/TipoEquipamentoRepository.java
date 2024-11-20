package com.senai.S.A.repository;

import com.senai.S.A.model.TipoEquipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoEquipamentoRepository extends JpaRepository<TipoEquipamento, Long> {
}
