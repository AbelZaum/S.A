package com.senai.S.A.repository;

import com.senai.S.A.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {

    // Consulta para listar equipamentos por tipo
    @Query("SELECT e FROM Equipamento e WHERE e.tipo.id = :tipoId")
    List<Equipamento> findByTipoId(@Param("tipoId") Long tipoId);
}
